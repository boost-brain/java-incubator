import Vue from 'vue'
import VueResource from 'vue-resource'

Vue.use(VueResource)

Vue.http.interceptors.push((request, next) => {
    request.headers.set('sessionId', 'f3a4be2c-c12f-4b5a-a149-43b6f95d34d5')
    // request.headers.set('Accept', 'application/json')
    next()
})

const url = 'http://localhost:9000/api/tasks';

export default {
    count: () => Vue.resource(url + '/count').get(),
    get: page => Vue.resource(url + '/page/'+ page +'/2').get(),
    add: task => Vue.resource(url + '/create').save(task),
    update: task => Vue.resource(url + '/update').update(task),
    remove: id => Vue.resource(url + '/delete{/id}').remove({id})
}
