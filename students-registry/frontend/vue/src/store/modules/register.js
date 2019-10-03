import registerApi from "../../api/register";

export default {
    mutations: {
        registerMutation: function (state, user) {
            console.log("registerMutation")
            // state.users = [
            //     ...state.users,
            //     user
            // ]
        }
    },
    actions: {
        async registerAction({commit}, user) {
            const result = await registerApi.register(user)
            const data = await result.json()
            commit('registerMutation', data)
        }
    }
}
