import Vue from 'vue'
import Router from 'vue-router'
import Projects from './views/Projects'
import Project from './components/Project'
import Users from './views/Users'
import Tasks from './views/Tasks'
import NewUser from './components/NewUser'
import Login from './components/Login'
import store from './store'
import {AUTH_LOGOUT} from './store/actions/auth'

Vue.use(Router)

const ifNotAuthenticated = (to, from, next) => {
    if (!store.getters.isAuthenticated) {
        next()
        return
    }
    next('/')
}

const ifAuthenticated = (to, from, next) => {
    console.log(store.getters.getToken)
    if (store.getters.isAuthenticated) {
        next()
        return
    }
    next('/login')
}

const logout = (to, from, next) => {
    console.log("logout")
    store.dispatch(AUTH_LOGOUT)
    next()
    return
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
            path: '/tasks',
            name: 'tasks',
            component: Tasks,
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
        {
            path: '/logout',
            name: 'Logout',
            component: Login,
            beforeEnter: logout,
        },
    ]
})
