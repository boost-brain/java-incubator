import Vue from 'vue'
import VueResource from 'vue-resource'

Vue.use(VueResource)

const url = 'http://localhost:9000/api/auth';

export default {
    login: credentials => Vue.resource(url + '/login').save(credentials)
}
