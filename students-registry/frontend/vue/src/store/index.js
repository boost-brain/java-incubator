import Vue from 'vue'
import Vuex from 'vuex'
import user from './modules/user'
import auth from './modules/auth'
import users from './modules/users'
import projects from './modules/projects'

Vue.use(Vuex)

export default new Vuex.Store({
    modules: {
        user,
        auth,
        users,
        projects
    },
    state: {
        title: 'Hello from store'
    },
    getters: {
        title (state) {
            return state.title + '!!!'
        }
    }
})
