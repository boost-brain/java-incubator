import {AUTH_ERROR, AUTH_LOGOUT, AUTH_REQUEST, AUTH_SUCCESS} from '../actions/auth'
// import { USER_REQUEST } from '../actions/user'
import apiCall from '../../api/api'

const state = {
    token: localStorage.getItem('user-token') || '',
    status: '',
    hasLoadedOnce: false }

const getters = {
    isAuthenticated: state => !!state.token,
    authStatus: state => state.status,
    getToken: state => state.token
}

const actions = {
    async AUTH_REQUEST({commit, state}, user) {
        console.log("AUTH_REQUEST run")
        const result = await apiCall.login(user)
        const resp = await result.json()
        console.log(resp)
        if(resp.sessionId != null){
            localStorage.setItem('user-token', resp)//.sessionId)
            console.log(resp)
            commit(AUTH_SUCCESS, state)
        }else{
            console.log("AUTH FAILED")
            commit(AUTH_ERROR, state)
            localStorage.removeItem('user-token')
        }
    },
    // [AUTH_REQUEST]: ({commit, dispatch}, user) => {
    //   return new Promise((resolve, reject) => {
    //     console.log("AUTH_REQUEST")
    //     commit(AUTH_REQUEST)
    //     apiCall({url: 'login', data: user, method: 'POST'})
    //     .then(resp => {
    //       localStorage.setItem('user-token', resp.token)
    //       // Here set the header of your ajax library to the token value.
    //       // example with axios
    //       // axios.defaults.headers.common['Authorization'] = resp.token
    //       commit(AUTH_SUCCESS, resp)
    //       dispatch(USER_REQUEST)
    //       resolve(resp)
    //     })
    //     .catch(err => {
    //       commit(AUTH_ERROR, err)
    //       localStorage.removeItem('user-token')
    //       reject(err)
    //     })
    //   })
    // },
    // // [AUTH_LOGOUT]: ({commit, dispatch}) => {
    // [AUTH_LOGOUT]: ({commit}) => {
    //   // return new Promise((resolve, reject) => {
    //   return new Promise((resolve) => {
    //     commit(AUTH_LOGOUT)
    //     localStorage.removeItem('user-token')
    //     resolve()
    //   })
    // }
}

const mutations = {
    [AUTH_REQUEST]: (state) => {
        console.log("AUTH_REQUEST mutation")
        state.status = 'loading'
    },
    [AUTH_SUCCESS]: (state, resp) => {
        console.log("AUTH_SUCCESS mutation")
        state.status = 'success'
        state.token = resp.token
        state.hasLoadedOnce = true
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
