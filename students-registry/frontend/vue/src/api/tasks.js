import Vue from 'vue'
import VueResource from 'vue-resource'

Vue.use(VueResource)

Vue.http.interceptors.push((request, next) => {
    request.headers.set('sessionId', 'dae0a13e-3932-42e5-9b81-a51d9b74089b')
    // request.headers['sessionId'] = "ec8aea0e-8832-4c29-a798-a11ef6707ff1"
    console.log(request)
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