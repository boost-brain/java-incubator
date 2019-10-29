export default {
  state: {
    loading: false,
    error: {},
    info: {}
  },
  mutations: {
    setLoading (state, payload) {
      state.loading = payload
    },
    setError (state, payload) {
      console.log("setError mutation run")
      console.log(payload)
      state.error = payload
      console.log("setError mutation OK")
    },
    clearError (state) {
      state.error = {}
    },
    setInfo (state, payload) {
      state.info = payload
    },
    clearInfo (state) {
      state.info = {}
    }
  },
  actions: {
    setLoading ({commit}, payload) {
      commit('setLoading', payload)
    },
    setError ({commit}, payload) {
      commit('setError', payload)
    },
    clearError ({commit}) {
      commit('clearError')
    },
    setInfo ({commit}, payload) {
      commit('setInfo', payload)
    },
    clearInfo ({commit}) {
      commit('clearInfo')
    }
  },
  getters: {
    getLoading (state) {
      return state.loading
    },
    getError (state) {
      return state.error
    },
    getInfo (state) {
      return state.info
    }
  }
}
