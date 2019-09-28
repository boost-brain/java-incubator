import Vue from 'vue'
import VueResource from 'vue-resource'

Vue.use(VueResource)

Vue.http.options.credentials = true

Vue.http.interceptors.push((request, next) => {
    request.credentials = true
    // request.headers.set('Accept', 'application/json')
    request.headers.set('sessionId', 'ec8aea0e-8832-4c29-a798-a11ef6707ff1')
    request.headers.set('Authorization', 'Bearer eyJ0e.....etc')
    request.headers.set('Accept', 'application/json')
    request.headers['sessionId'] = "ec8aea0e-8832-4c29-a798-a11ef6707ff1"
    console.log(request)
    next()
})

const url = 'http://localhost:8084/api/tasks';

export default {
    count: () => Vue.resource(url + '/count').get(),
    get: page => Vue.resource(url + '/page/'+ page +'/2').get(),
    add: task => Vue.resource(url + '/create').save(task),
    update: task => Vue.resource(url + '/update').update(task),
    remove: id => Vue.resource(url + '/delete{/id}').remove({id})
}