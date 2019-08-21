import Vue from 'vue'
import VueResource from 'vue-resource'

Vue.use(VueResource)

const points = Vue.resource('http://localhost:8080/findById{/id}')
export default {
    add: point => points.save({}, point),
    update: point => points.update({id: point['id']}, point),
    remove: id => points.remove({id})
}
