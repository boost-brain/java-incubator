import Vue from 'vue'
import Router from 'vue-router'
import Projects from './views/Cards.vue'
import Users from './views/Users'

import Point from './components/Point.vue'

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
            path: '/point/:id',
            props: true,
            name: 'point',
            component: Point
        },
    ]
})
