import Vue from 'vue'
import Vuex from 'vuex'
import restApi from './api/points'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        points: [],
        isLoading: false,
        resource: {}
    },
    mutations: {

        createPoint (state, payload) {
            state.points.push(payload)
        },
        addPointMutation: function (state, point) {
            console.log("addPointMutation")
            state.points = [
                ...state.points,
                point
            ]
        },
        updatePointMutation(state, point) {
            console.log("updatePointMutation")
            const updateIndex = state.points.findIndex(item => item.id === point['id'])
            state.points = [
                ...state.points.slice(0, updateIndex),
                point,
                ...state.points.slice(updateIndex + 1)
            ]
        },
        removePointMutation(state, point) {
            console.log("removePointMutation")
            const deletionIndex = state.points.findIndex(item => item['id'] === point['id'])

            if (deletionIndex > -1) {
                state.points = [
                    ...state.points.slice(0, deletionIndex),
                    ...state.points.slice(deletionIndex + 1)
                ]
            }
        },
        setLoading(state, payload){
            state.isLoading = payload
        }
    },
    actions: {

        async loadAction ({commit}) {
            commit('setLoading', true)
            await this.state.resource.get().then(response => response.json())
                .then(points => {
                    for (var data of points) {
                        commit('addPointMutation', data)
                    }
                })
            commit('setLoading', false)
        },
        async addPointAction({commit, state}, point) {
            const result = await restApi.add(point)
            const data = await result.json()
            const index = state.points.findIndex(item => item['id'] === data['@id'])
            console.log(index)
            if (index > -1) {
                commit('updatePointMutation', data)
            } else {
                commit('addPointMutation', data)
            }
        },
        async updatePointAction({commit}, point) {
            const result = await restApi.update(point)
            console.log(point)
            const data = await result.json()
            commit('updatePointMutation', data)
        },
        async removePointAction({commit}, point) {
            const result = await restApi.remove(point.projectId)
            if (result.ok) {
                commit('removePointMutation', point)
            }
        },
    },
    getters: {
        points (state) {
            return state.points
        },
        myPoints (state) {
            return state.points
        },
        pointById (state) {
            return pointId => {
                return state.points.find(point => point.id == pointId)
            }
        }
    },
    setters:{

    }
})
