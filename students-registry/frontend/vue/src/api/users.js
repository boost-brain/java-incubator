import Vue from 'vue'
import VueResource from 'vue-resource'

Vue.use(VueResource)

const url = 'http://localhost:9000/api/users';

export default {
    count: () => Vue.resource(url + '/count').get(),
    get: page => Vue.resource(url + '/page/'+ page +'/2').get(),
    add: user => Vue.resource(url + '/create').save(user),
    update: user => Vue.resource(url + '/update').update(user),
    remove: email => Vue.resource(url + '/delete{/email}').remove({email})
}
