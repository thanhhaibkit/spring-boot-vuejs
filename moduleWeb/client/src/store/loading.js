export default {
  namespaced : true,
  state: {
    loading: false
  },
  getters: {
    isLoading: state => {
      return state.loading
    }
  },
  mutations: {
    SET_LOADING(state, loading) {
      state.loading = loading;
    }
  },
  actions: {
    setLoading({ commit }, loading) {
      commit("SET_LOADING", loading);
    }
  }
};