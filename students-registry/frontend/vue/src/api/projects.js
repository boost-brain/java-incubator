import Vue from 'vue'
import VueResource from 'vue-resource'

Vue.use(VueResource)

const url = 'http://localhost:9000/api/projects';

export default {
    get: () => Vue.resource(url + '/projects-all').get(),
    find: projectId => Vue.resource(url + '/findById{/projectId}').get(projectId),
    add: project => Vue.resource(url + '/createProject{/id}').save({}, project),
    update: project => Vue.resource(url + '/update').save(project),
    remove: project_id => Vue.resource(url + '/delete{/id}').remove({project_id})
}
