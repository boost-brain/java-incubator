import {USER_ERROR, USER_REQUEST, USER_SUCCESS} from '../actions/user'
import apiCall from '../../api/api'
import Vue from 'vue'
import {AUTH_LOGOUT} from '../actions/auth'

const state = { status: '', profile: {} }

const getters = {
    getProfile: state => state.profile,
    isProfileLoaded: state => !!state.profile.name,
}

const actions = {
    [USER_REQUEST]: ({commit, dispatch}) => {
        commit(USER_REQUEST)
        apiCall({url: 'user/me'})
            .then(resp => {
                console.log(resp)
                commit(USER_SUCCESS, resp)
            })
            // .catch(resp => {
            .catch(() => {
                commit(USER_ERROR)
                // if resp is unauthorized, logout, to
                dispatch(AUTH_LOGOUT)
            })
    },
}

const mutations = {
    [USER_REQUEST]: (state) => {
        console.log("USER_REQUEST mutation")
        state.status = 'loading'
    },
    [USER_SUCCESS]: (state, resp) => {
        console.log("USER_SUCCESS mutation")
        state.status = 'success'
        Vue.set(state, 'profile', resp)
    },
    [USER_ERROR]: (state) => {
        console.log("USER_ERROR mutation")
        state.status = 'error'
    },
    [AUTH_LOGOUT]: (state) => {
        console.log("AUTH_LOGOUT mutation")
        state.profile = {}
    }
}

export default {
    state,
    getters,
    actions,
    mutations,
}
