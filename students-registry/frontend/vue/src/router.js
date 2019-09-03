import Vue from 'vue'
import Router from 'vue-router'
import Projects from './views/Projects'
import Project from './components/Project'
import Users from './views/Users'
import NewUser from './components/NewUser'
import Login from './components/Login'
import store from './store'

// import Project from './components/Project.vue'

Vue.use(Router)

const ifNotAuthenticated = (to, from, next) => {
    console.log("ifNotAuthenticated")
    if (!store.getters.isAuthenticated) {
        next()
        return
    }
    next('/')
}

const ifAuthenticated = (to, from, next) => {
    console.log("ifAuthenticated")
    console.log(store.getters.isAuthenticated)
    console.log(store.getters.getToken)
    if (store.getters.isAuthenticated) {
        next()
        return
    }
    console.log("next to login page")
    next('/login')
}

export default new Router({
    mode: 'history',
    routes: [
        {
            path: '/',
            name: 'base',
            component: Projects,
            beforeEnter: ifAuthenticated
        },
        {
            path: '/projects',
            name: 'projects',
            component: Projects,
            beforeEnter: ifAuthenticated
        },
        {
            path: '/users',
            name: 'users',
            component: Users,
            beforeEnter: ifAuthenticated
        },
        {
            path: '/project/:id',
            props: true,
            name: 'project',
            component: Project,
            beforeEnter: ifAuthenticated
        },
        {
            path: '/new',
            name: 'newUser',
            component: NewUser,
            beforeEnter: ifAuthenticated
        },
        {
            path: '/login',
            name: 'Login',
            component: Login,
            beforeEnter: ifNotAuthenticated,
        },
    ]
})
