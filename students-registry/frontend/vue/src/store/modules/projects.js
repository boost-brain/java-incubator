import projectApi from '../../api/projects'

export default {
    state: {
        projects: [],
        isLoading: false,
        resource: {}
    },
    mutations: {
        setProjects (state, payload) {
            console.log("setProjects mutation")
            state.projects = payload
        },
        createProject (state, payload) {
            state.projects.push(payload)
        },
        addProjectMutation: function (state, project) {
            console.log("addProjectMutation")
            state.projects = [
                ...state.projects,
                project
            ]
        },
        updateProjectMutation(state, project) {
            console.log("updateProjectMutation")
            const updateIndex = state.projects.findIndex(item => item.id === project['id'])
            state.projects = [
                ...state.projects.slice(0, updateIndex),
                project,
                ...state.projects.slice(updateIndex + 1)
            ]
        },
        removeProjectMutation(state, project) {
            console.log("removeProjectMutation")
            const deletionIndex = state.projects.findIndex(item => item['id'] === project['id'])

            if (deletionIndex > -1) {
                state.projects = [
                    ...state.projects.slice(0, deletionIndex),
                    ...state.projects.slice(deletionIndex + 1)
                ]
            }
        },
        setLoading(state, payload){
            state.isLoading = payload
        },
    },
    getters: {
        setProjects (state) {
            return state.projects
        },
        projects (state) {
            return state.projects
        },
        projectById (state) {
            return projectId => {
                return state.projects.find(project => project.id == projectId)
            }
        }
    },
    actions: {

        async loadAction ({commit}) {
            commit('setLoading', true)
            await this.state.resource.get().then(response => response.json())
                .then(projects => {
                    for (var data of projects) {
                        commit('addProjectMutation', data)
                    }
                })
            commit('setLoading', false)
        },

        async addProjectAction({commit, state}, project) {
            const result = await projectApi.add(project)
            const data = await result.json()
            const index = state.projects.findIndex(item => item['projectId'] === data['@projectId'])
            console.log(index)
            if (index > -1) {
                commit('updateProjectMutation', data)
            } else {
                commit('addProjectMutation', data)
            }
        },
        async updateProjectAction({commit}, project) {
            const result = await projectApi.update(project)
            console.log(project)
            const data = await result.json()
            commit('updateProjectMutation', data)
        },
        async removeProjectAction({commit}, project) {
            const result = await projectApi.remove(project.projectId)
            if (result.ok) {
                commit('removeProjectMutation', project)
            }
        },
    },
}
