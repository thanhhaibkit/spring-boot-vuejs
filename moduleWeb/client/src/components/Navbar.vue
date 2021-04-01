<template>
  <b-navbar type="light" class="box-shadow">
    <b-collapse id="nav-collapse" is-nav>
      <b-navbar-nav>
        <b-nav-item v-if="isLoggedIn" to="/dashboard"
          ><b-icon-house-fill class="mr-2"></b-icon-house-fill>
          ダッシュボード</b-nav-item
        >
        <b-nav-item v-if="!isLoggedIn" to="/"
          ><b-icon-house-fill class="mr-2"></b-icon-house-fill
          >ホーム</b-nav-item
        >
      </b-navbar-nav>
      <b-navbar-nav class="ml-auto">
        <b-nav-item v-if="!isLoggedIn" :to="{ name: 'Login' }"
          ><b-icon-box-arrow-in-right class="mr-2"></b-icon-box-arrow-in-right
          >ログイン</b-nav-item
        >
        <b-nav-item-dropdown
          v-if="isLoggedIn && !isRoleOperator && !isRoleAdmin"
          right
          no-caret
          no-flip
        >
          <template #button-content>
            <b-icon-person-circle></b-icon-person-circle>
          </template>
          <b-dropdown-header>WELCOME!</b-dropdown-header>
          <b-dropdown-item href="#" @click="handleLogout"
            ><b-icon-box-arrow-right class="mr-2"></b-icon-box-arrow-right
            >ログアウト</b-dropdown-item
          >
        </b-nav-item-dropdown>
      </b-navbar-nav>
    </b-collapse>
  </b-navbar>
</template>

<script>
import { mapActions, mapGetters } from "vuex";

export default {
  data() {
    return {};
  },
  computed: {
    isLoggedIn() {
      return this.userInfo && this.userInfo.isLogin;
    },
    isRoleOperator() {
      return (
        this.userInfo &&
        this.userInfo.roles &&
        this.userInfo.roles.includes("ROLE_OPERATOR")
      );
    },
    isRoleAdmin() {
      return (
        this.userInfo &&
        this.userInfo.roles &&
        this.userInfo.roles.includes("ROLE_ADMIN")
      );
    },
    ...mapGetters({
      userInfo: "user/getInfoUser",
    }),
  },
  methods: {
    ...mapActions("user", ["removeInfoUser"]),
    handleLogout() {
      this.removeInfoUser();
      this.$router.push("/login");
    },
  },
};
</script>

<style lang="scss" scoped>
.box-shadow {
  -webkit-box-shadow: 2px 2px 5px 0px rgba(60, 60, 60, 0.7);
  -moz-box-shadow: 2px 2px 5px 0px rgba(60, 60, 60, 0.7);
  box-shadow: 2px 2px 5px 0px rgba(60, 60, 60, 0.7);
}
</style>
