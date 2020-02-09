import Vue from 'vue'
import Router from 'vue-router'
import Projects from './views/Projects'
import Project from './components/Project'
import Users from './views/Users'
import Tasks from './views/Tasks'
import Profile from './views/Profile'
import NewUser from './components/NewUser'
import Login from './components/Login'
import store from './store'
import Task from "./components/Task";

Vue.use(Router)

const ifNotAuthenticated = (to, from, next) => {
    if (!store.getters.isAuthenticated) {
        next()
        return
    }
    next('/profile')
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
    store.dispatch('AUTH_LOGOUT')
    next()
    return
}

export default new Router({
    mode: 'history',
    routes: [
        {
            path: '/',
            name: 'base',
            component: Users,
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
            path: '/task/:id',
            props: true,
            name: 'task',
            component: Task,
            beforeEnter: ifAuthenticated
        },
        {
            path: '/new',
            name: 'newUser',
            component: NewUser,
            beforeEnter: ifNotAuthenticated
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
        {
            path: '/profile',
            name: 'Profile',
            component: Profile,
            beforeEnter: ifAuthenticated,
        },
    ]
})
