import Vue from 'vue'
import Router from 'vue-router'
// import Table from './views/Table.vue'
import Projects from './views/Cards.vue'

import Point from './components/Point.vue'
import Home from './views/Home'

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
            path: '/home',
            name: 'home',
            component: Home
        },
        {
            path: '/projects',
            name: 'projects',
            component: Projects
        },
        {
            path: '/about',
            name: 'about',
            component: () => import('./views/About.vue')
        },
        {
            path: '/point/:id',
            props: true,
            name: 'point',
            component: Point
        },
    ]
})
