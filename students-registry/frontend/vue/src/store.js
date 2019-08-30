import Vue from 'vue'
import Vuex from 'vuex'
import pointApi from './api/points'
import userApi from './api/users'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        points: [],
        users: [],
        userCount: 0,
        isLoading: false,
        resource: {}
    },
    mutations: {
        setUsers (state, payload) {
            state.users = payload
        },
        emptyUsers (state){
            state.users = []
        },
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
        createUser (state, payload) {
            state.users.push(payload)
        },
        addUserMutation: function (state, user) {
            console.log("addUserMutation")
            state.users = [
                ...state.users,
                user
            ]
        },
        updateUserMutation(state, user) {
            console.log("updateUserMutation")
            const updateIndex = state.users.findIndex(item => item.email === user['email'])
            state.users = [
                ...state.users.slice(0, updateIndex),
                user,
                ...state.users.slice(updateIndex + 1)
            ]
        },
        removeUserMutation(state, user) {
            console.log("removeUserMutation")
            const deletionIndex = state.users.findIndex(item => item['email'] === user['@email'])

            if (deletionIndex > -1) {
                state.users = [
                    ...state.users.slice(0, deletionIndex),
                    ...state.users.slice(deletionIndex + 1)
                ]
            }
        },
        setLoading(state, payload){
            state.isLoading = payload
        },
        setUserCount(state, payload){
            console.log('setUserCount')
            console.log(payload)
            state.userCount = payload
            console.log(state.userCount)
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
            const result = await pointApi.add(point)
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
            const result = await pointApi.update(point)
            console.log(point)
            const data = await result.json()
            commit('updatePointMutation', data)
        },
        async removePointAction({commit}, point) {
            const result = await pointApi.remove(point.projectId)
            if (result.ok) {
                commit('removePointMutation', point)
            }
        },

        async loadUsersAction ({commit}) {
            commit('setLoading', true)
            await this.state.resource.get().then(response => response.json())
                .then(users => {
                    for (var data of users) {
                        commit('addUserMutation', data)
                    }
                })
            commit('setLoading', false)
        },
        async addUserAction({commit, state}, user) {
            const result = await userApi.add(user)
            const data = await result.json()
            const index = state.users.findIndex(item => item['id'] === data['@id'])
            console.log(index)
            if (index > -1) {
                commit('updateUserMutation', data)
            } else {
                commit('addUserMutation', data)
            }
        },
        async updateUserAction({commit}, user) {
            const result = await userApi.update(user)
            console.log(user)
            const data = await result.json()
            commit('updateUserMutation', data)
        },
        async removeUserAction({commit}, user) {
            const result = await userApi.remove(user.email)
            if (result.ok) {
                commit('removeUserMutation', user)
            }
        },
        async getUserCount({commit}) {
            console.log('getUserCount')
            const result = await userApi.count()
            const count = await result.json()
            commit('setUserCount', count)
        },
    },
    getters: {
        getUserCount (state) {
            return state.userCount
        },
        points (state) {
            return state.points
        },
        users (state) {
            return state.users
        },
        myPoints (state) {
            return state.points
        },
        pointById (state) {
            return pointId => {
                return state.points.find(point => point.id == pointId)
            }
        }
    }
})
