import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex);

// Import stores
import loading from "./loading";

const store = new Vuex.Store({
  modules: {
    loading
  },
  plugins: [createPersistedState({
    paths: ['user']
  })]
});

export default store;