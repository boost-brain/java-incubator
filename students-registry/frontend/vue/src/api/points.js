import Vue from 'vue'
import VueResource from 'vue-resource'

Vue.use(VueResource)

// const points = Vue.resource('http://localhost:8080/findById{/id}')
export default {
    add: point => Vue.resource('http://localhost:8080/createProject{/id}').save({}, point),
    // update: point => Vue.resource('http://localhost:8080/update{/id}').update({id: point.projectId}, point),
    update: point => Vue.resource('http://localhost:8080/update').save(point),
    // remove: id => Vue.resource('http://localhost:8080/deleteById{/id}').remove({id})
    remove: id => Vue.resource('http://localhost:8080/delete{/id}').remove({id})
}
