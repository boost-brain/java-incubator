import Vue from 'vue'
import VueResource from 'vue-resource'

Vue.use(VueResource)

const url = 'http://localhost:9000/api/register';

export default {
    register: user => Vue.resource(url + '/create').save(user),
}
