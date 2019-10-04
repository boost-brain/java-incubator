import Vue from 'vue'
import App from './App.vue'
import 'material-design-icons-iconfont/dist/material-design-icons.css'
import router from './router'
import store from './store'
import vuetify from './plugins/vuetify';
import Vuelidate from 'vuelidate'
import VueResource from 'vue-resource'


Vue.use(VueResource)
Vue.use(Vuelidate)

new Vue({
    router,
    store,
    vuetify,
    render: h => h(App)
}).$mount('#app')