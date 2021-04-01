import Vue from "vue"
import App from "./App.vue"
import router from "./router";
//import store from "@/store";

Vue.config.devtools = true;
Vue.config.productionTip = false

// Load plugins
import "./plugins";

// Moment
import moment from "moment";
Vue.prototype.moment = moment;

// Global stylesheet
require("./assets/scss/main.scss");

new Vue({
  router,
  //store,
  render: h => h(App),
}).$mount("#app")
