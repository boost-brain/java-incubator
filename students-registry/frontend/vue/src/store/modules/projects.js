import projectApi from '../../api/projects'

export default {
    state: {
        projects: [],
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
        emptyProjects (state){
            state.projects = []
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
        }
    },
    getters: {
        getProjects (state) {
            return state.projects
        },
        projectById (state) {
            return projectId => {
                return state.projects.find(project => project.id == projectId)
            }
        }
    },
    actions: {
        async getProjectsByIdList ({commit}, Ids) {
            console.log('getProjectsByIdList run')
            var uniqueIds = new Set(Ids)
            console.log(uniqueIds)
            commit('setLoading', true)

            const result = await projectApi.get()
            const data = await result.json()
            console.log(data)

            const data2 = data.filter(x => uniqueIds.has(x.projectId))
            const data3 = Array.from(data2)
            console.log(data3)

            for (var proj of data3) {
                console.log(proj)
                commit('addProjectMutation', proj)
            }

            commit('setLoading', false)
        },

        async loadAction ({commit}) {
            commit('setLoading', true)
            const result = await projectApi.get()
            const data = await result.json()

            for (var proj of data) {
                console.log(proj)
                commit('addProjectMutation', proj)
            }
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
