import apiCall from '../../api/api'
import usersCall from '../../api/users'
// import {mapMutations} from 'vuex'

const state = {
    token: '',
    sessionId: '',
    status: '',
    hasLoadedOnce: false,
    user: {
        name: '',
        email: ''
    },
    error: {},
}

const getters = {
    isAuthenticated: state => !!state.token,
    authStatus: state => state.status,
    getToken: state => state.token,
    getSessionId: () =>  state.sessionId,
    getUser: state => state.user,
    // getAuthError: state => state.error
}

const actions = {
    async AUTH_REQUEST({commit, state}, user) {
        commit('setLoading', true)
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
                //объединяем два ответа в один токен
                const resp3 = {
                    ...resp,
                    ...resp2
                }
                commit('AUTH_SUCCESS', resp3)
                commit('clearError')
                commit('setInfo', {"body": "Пользователь " + user.login + " авторизован"})
            } else {
                commit('setError', {"body": "Пользователь не авторизован"})
                console.log("AUTH FAILED")
                commit('AUTH_ERROR', state)
            }
        }catch(err){
            console.log(err)
        }finally {
            commit('setLoading', false)
        }
    },
    AUTH_LOGOUT ({ commit }) {
        commit('AUTH_LOGOUT')
        commit('clearError')
        commit('clearInfo')
    }
}

const mutations = {
    AUTH_REQUEST: (state) => {
        console.log("AUTH_REQUEST mutation")
        state.status = 'loading'
    },
    AUTH_SUCCESS: (state, resp) => {
        console.log("AUTH_SUCCESS mutation")
        state.status = 'success'
        state.token = resp
        state.hasLoadedOnce = true
    },
    setUser: (state, resp) => {
        console.log("SetUser mutation")
        state.user = resp
    },
    AUTH_ERROR: (state) => {
        state.status = 'error'
    },
    AUTH_LOGOUT: (state) => {
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