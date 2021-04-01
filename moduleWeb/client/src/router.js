import Vue from "vue";
import VueRouter from "vue-router";
//import store from "@/store";

Vue.use(VueRouter);

import Home from "./views/Home";
import Index from "./views/Index";

const routes = [
  {
    path: "/",
    name: "Index",
    component: Index,
    children: [
      {
        path: "/",
        name: "Home",
        component: Home,
        meta: {
          isPublic: true,
        },
      }
    ],
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

router.beforeEach((to, from, next) => {
  const { isPublic, roles } = to.meta;

  if (!isPublic) {
    if (isLoggedIn) {
      const currentUser = store.getters["user/getInfoUser"];
      const hasRole =
        roles &&
        roles.some(
          (r) =>
            currentUser && currentUser.roles && currentUser.roles.includes(r)
        );

      if (hasRole) {
        next();
      } else {
        next("/dashboard");
      }
    } else {
      return next("/login");
    }
  } else {
    next();
  }
});

export default router;
