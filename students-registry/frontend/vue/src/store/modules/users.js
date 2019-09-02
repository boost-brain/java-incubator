import userApi from "../../api/users";

export default {
    state: {
        users: [],
        userCount: 0,
    },
    mutations: {
        setUsers (state, payload) {
            console.log("setUsers mutation")
            state.users = payload
        },
        emptyUsers (state){
            state.users = []
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
        setUserCount(state, payload){
            console.log('setUserCount mutation')
            state.userCount = payload
            console.log(state.userCount)
        }
    },
    actions: {
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
            console.log('getUserCount action')
            const result = await userApi.count()
            const count = await result.json()
            console.log(count)
            commit('setUserCount', count)
        },
    },
    getters: {
        getUserCount (state) {
            return state.userCount
        },
        users (state) {
            return state.users
        },
    }
}
