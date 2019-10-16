import registerApi from "../../api/register";

export default {
    state: {
        users: [],
        isLoading: false,
        info: '',
        error: {},
    },
    mutations: {
        registerMutation: function (state, user) {
            console.log("registerMutation")
            state.users = [
                ...state.users,
                user
            ]
        },
        setLoading(state, payload){
            state.isLoading = payload
        },
        setInfoMessage(state, payload) {
            state.info = payload
        },
        setErrorMessage(state, payload) {
            console.log(payload)
            state.error = payload
        }
    },
    actions: {
        async registerAction({commit}, user) {
            commit('setLoading', true)
            try {
                const result = await registerApi.register(user)
                const data = await result.json()
                console.log(data)
                commit('registerMutation', data)
                commit('setInfoMessage', 'Пользователь успешно зарегестрирован')
                commit('setErrorMessage', '')
            }catch(e){
                console.log(e)
                commit('setErrorMessage', e)
            }finally {
                commit('setLoading', false)
            }
        }
    },
    getters: {
        getIsLoading(state) {
            return state.isLoading
        },
        getInfoMessage(state) {
            return state.info
        },
        getErrorMessage(state) {
            return state.error
        },
    }
}
