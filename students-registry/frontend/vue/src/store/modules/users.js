import userApi from "../../api/users";
// import taskApi from "../../api/tasks";

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
            const updateIndex = state.users.findIndex(item => item.email === user['email'])
            console.log("updateIndex=" + updateIndex)
            if(updateIndex == -1){
                this.commit('addUserMutation',user)
            }else{
                state.users = [
                    ...state.users.slice(0, updateIndex),
                    user,
                    ...state.users.slice(updateIndex + 1)
                ]
            }
        },
        updateProfileUserMutation(state, user) {
            console.log("updateProfileUserMutation")
            console.log(user)
            const updateIndex = state.users.findIndex(item => item.email === user['email'])
            console.log("updateIndex=" + updateIndex)
            if(updateIndex == -1){
                this.commit('addUserMutation',user)
            }else{
                state.users = [
                    ...state.users.slice(0, updateIndex),
                    user,
                    ...state.users.slice(updateIndex + 1)
                ]
            }
            this.commit('setUser',user)
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
        }
    },
    actions: {
        async loadUsersAction ({commit}, pageN) {
            commit('setLoading', true)
            const result = await userApi.get(pageN)
            const users = await result.json()
            for (var user of users) {
                commit('addUserMutation', user)
            }
            // await this.state.resource.get().then(response => response.json())
            //     .then(users => {
            //         for (var data of users) {
            //             commit('addUserMutation', data)
            //         }
            //     })
            commit('setLoading', false)
        },
        async addUserAction({commit, state}, user) {
            const result = await userApi.add(user)
            const data = await result.json()
            const index = state.users.findIndex(item => item['id'] === data['@id'])
            if (index > -1) {
                commit('updateUserMutation', data)
            } else {
                commit('addUserMutation', data)
            }
        },
        async updateUserAction({commit}, user) {
            const result = await userApi.update(user)
            console.log(result)
            if(result.bodyText == 'OK') {
                commit('updateUserMutation', user)
            }
        },
        async updateProfileUserAction({commit}, user) {
            const result = await userApi.update(user)
            console.log(result)
            if(result.bodyText == 'OK') {
                commit('updateProfileUserMutation', user)
            }
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
