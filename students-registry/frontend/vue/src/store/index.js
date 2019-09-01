import Vue from 'vue'
import Vuex from 'vuex'
// import store from './store'
import users from './users'
import projects from './projects'

Vue.use(Vuex)

export default new Vuex.Store({
    modules: {
        // store,
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
