import {AUTH_ERROR, AUTH_LOGOUT, AUTH_REQUEST, AUTH_SUCCESS} from '../actions/auth'
import apiCall from '../../api/api'
import usersCall from '../../api/users'

const state = {
    token: '',
    sessionId: '',
    status: '',
    // error: '',
    hasLoadedOnce: false,
    user: {
        name: '',
        email: ''
    }
}

const getters = {
    isAuthenticated: state => !!state.token,
    authStatus: state => state.status,
    getToken: state => state.token,
    getSessionId: () =>  state.sessionId,
    getUser: state => state.user
}

const actions = {
    async AUTH_REQUEST({commit, state}, user) {
        try {
            console.log("AUTH_REQUEST action run.")
            const result = await apiCall.login(user)
            const resp = await result.json()
            console.log(resp)
            if (resp.sessionId != null) {
                //получаем User
                const result2 = await usersCall.read(user.login)
                const resp2 = await result2.json()
                commit('setUser', resp2)
                //сохраняем полученные credentials
                state.sessionId = resp.sessionId
                console.log(resp2)
                commit(AUTH_SUCCESS, resp)
                //объединяем два ответа в один токен
                const resp3 = {
                    ...resp,
                    ...resp2
                }
                console.log(resp3)

            } else {
                state.error = "unauth"
                console.log("AUTH FAILED")
                commit(AUTH_ERROR, state)
            }
        }catch(err){
            console.log(err)
        }
    },
    [AUTH_LOGOUT]: ({commit}) => {
        return new Promise((resolve) => {
            commit(AUTH_LOGOUT)
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
        state.status = 'success'
        state.token = resp
        state.hasLoadedOnce = true
        console.log("AUTH_SUCCESS mutation OK")
    },
    setUser: (state, resp) => {
        console.log("SetUser mutation")
        console.log(resp)
        state.user.email = resp.email
        state.user.name = resp.name
        console.log("SetUser mutation OK")
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
