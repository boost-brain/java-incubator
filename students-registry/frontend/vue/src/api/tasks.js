import Vue from 'vue'
import VueResource from 'vue-resource'
import store from '../store/modules/auth'

Vue.use(VueResource)

Vue.http.interceptors.push((request, next) => {
    request.headers.set('sessionId', store.getters.getSessionId())
    console.log(request)
    next()
})

const url = 'http://localhost:9000/api/tasks';

export default {
    count: () => Vue.resource(url + '/count').get(),
    get: page => Vue.resource(url + '/page/'+ page +'/2').get(),
    add: task => Vue.resource(url + '/create').save(task),
    update: task => Vue.resource(url + '/put').update(task),
    remove: id => Vue.resource(url + '/delete{/id}').remove({id}),
    for: user => Vue.resource(url + '/for{/user}').get({user})
}