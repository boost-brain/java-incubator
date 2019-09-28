import Vue from 'vue'
import App from './App.vue'
import 'material-design-icons-iconfont/dist/material-design-icons.css'
import router from './router'
import store from './store'
import vuetify from './plugins/vuetify';
import './api/resource'
import VueResource from 'vue-resource'


Vue.use(VueResource)

Vue.config.productionTip = false

// Vue.http.interceptors.push(request => {
//     request.headers.set('sessionId2', 'RAND TOKEN ' + Math.random())
//     console.log(request)
// })


new Vue({
    router,
    store,
    vuetify,
    render: h => h(App)
}).$mount('#app')