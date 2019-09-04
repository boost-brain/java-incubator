import Vue from 'vue'
import Vuex from 'vuex'
import auth from './modules/auth'
import users from './modules/users'
import projects from './modules/projects'

Vue.use(Vuex)

export default new Vuex.Store({
    modules: {
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
