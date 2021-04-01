import axios from "axios";
import store from "@/store";
import router from "@/router";

const namespace = "axios-retry";
function getCurrentState(request) {
  const currentState = request[namespace] || {};
  currentState.retryCount = currentState.retryCount || 0;
  request[namespace] = currentState;
  return currentState;
}
const api = axios.create({
  //baseURL: `http://localhost:${process.env.VUE_APP_PORT_APP || 8080}/api/v1`,
  baseURL: `/api/v1`,
  headers: {
    "Content-Type": "application/json;charset=UTF-8"
  }
});
api.defaults.timeout = 10000; // 10s
api.interceptors.request.use(request => {
  store.dispatch("loading/setLoading", true);
  /*
  const token = TokenService.getToken();
  if (token && !request.url.includes("login") && !request.url.includes("reservation")) {
    request.headers["Authorization"] = "Bearer " + token;
  }
  */
  const currentState = getCurrentState(request);
  currentState.lastRequestTime = Date.now();
  return request;
});

const responseHandler = (response) => {
  store.dispatch("loading/setLoading", false);
  return response;
};
//const axiosRetryCall = (error)  => {
//  const config = error.config;
//  // If we have no information to retry the request
//  if (config) {
//    const {
//      retries = 1,
//      retryCondition =  error => {
//        return !error.response && error.code == "ECONNABORTED" && error.message && error.message.toLowerCase().includes("timeout ") >- 1;
//      }
//    } = Object.assign({}, {
//      retries: 1,
//      retryCondition: error => {
//        return !error.response && error.code == "ECONNABORTED" && error.message && error.message.toLowerCase().includes("timeout ") >- 1;
//      }
//    }, config[namespace]);
//
//    const currentState = getCurrentState(config);
//    const shouldRetry = retryCondition(error) && currentState.retryCount < retries;
//    if (shouldRetry) {
//      currentState.retryCount += 1;
//      config.timeout = 10000;
//      return Promise.resolve(api(config));
//    }
//  }
//}
const errorHandler = (error) => {
  const response = error.response;
  if (response && response.status === 401 && response.data.message.includes("JWT expired")) {
    //TokenService.removeToken();
    store.dispatch("user/removeInfoUser");
    router.push("/login");
  }

//  if (error.config) {
//    return axiosRetryCall(error);
//  }
  
  store.dispatch("loading/setLoading", false);
  return Promise.reject(error);
};

api.interceptors.response.use(responseHandler, errorHandler);

export default api;