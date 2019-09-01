import Vue from 'vue'
import Router from 'vue-router'
import Projects from './views/Projects'
import Users from './views/Users'
import NewUser from './components/NewUser'

// import Project from './components/Project.vue'

Vue.use(Router)

export default new Router({
    mode: 'history',
    routes: [
        {
            path: '/',
            name: 'base',
            component: Projects
        },
        {
            path: '/projects',
            name: 'projects',
            component: Projects
        },
        {
            path: '/users',
            name: 'users',
            component: Users
        },
        {
            path: '/project/:id',
            props: true,
            name: 'project',
            component: Projects
        },
        {
            path: '/new',
            name: 'newUser',
            component: NewUser
        },
    ]
})
