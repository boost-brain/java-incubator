import {AUTH_ERROR, AUTH_LOGOUT, AUTH_REQUEST, AUTH_SUCCESS} from '../actions/auth'
import apiCall from '../../api/api'

const state = {
    token: localStorage.getItem('user-token') || '',
    sessionId: '123',
    status: '',
    hasLoadedOnce: false }

const getters = {
    isAuthenticated: state => !!state.token,
    authStatus: state => state.status,
    getToken: state => state.token,
    getSessionId: () =>  state.sessionId
}

const actions = {
    async AUTH_REQUEST({commit, state}, user) {
        try {
            console.log("AUTH_REQUEST action run.")
            const result = await apiCall.login(user)
            console.log("called")
            const resp = await result.json()
            console.log(resp)
            if (resp.sessionId != null) {
                state.sessionId = resp.sessionId
                console.log("session is not null ")
                console.log(state)
                localStorage.setItem('user-token', resp)
                commit(AUTH_SUCCESS, resp)
            } else {
                console.log("AUTH FAILED")
                commit(AUTH_ERROR, state)
                localStorage.removeItem('user-token')
            }
        }catch(err){
            console.log(err)
        }
    },
    [AUTH_LOGOUT]: ({commit}) => {
        return new Promise((resolve) => {
            commit(AUTH_LOGOUT)
            localStorage.removeItem('user-token')
            resolve()
        })
    }
}

const mutations = {
    [AUTH_REQUEST]: (state) => {
        console.log("AUTH_REQUEST mutation")
        state.status = 'loading'
    },
    [AUTH_SUCCESS]: (state, resp) => {
        console.log("AUTH_SUCCESS mutation")
        console.log(resp)
        state.status = 'success'
        state.token = resp
        state.hasLoadedOnce = true
        console.log("AUTH_SUCCESS mutation OK")
    },
    [AUTH_ERROR]: (state) => {
        console.log("AUTH_ERROR mutation")
        state.status = 'error'
        state.hasLoadedOnce = true
    },
    [AUTH_LOGOUT]: (state) => {
        console.log("AUTH_LOGOUT mutation")
        state.token = ''
    }
}

export default {
    state,
    getters,
    actions,
    mutations,
}
