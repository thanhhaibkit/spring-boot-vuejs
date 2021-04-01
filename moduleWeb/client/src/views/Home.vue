<template>
  <div class="home">
    <b-nav
      align="center"
    >
      <b-nav-item :to="{ path: '/my-page'}">My page</b-nav-item>
      <b-nav-item :to="{ path: '/login'}">Login</b-nav-item>
      <b-nav-item :to="{ path: `/reservation?code%3D${accountCode}`}">
        Reservation
      </b-nav-item>
      <b-nav-item :to="{ path: '/account-management'}">Account Management</b-nav-item>
    </b-nav>
  </div>
</template>

<script>
export default {
  name: "Home",
  data() {
    return {
      accountCode: "ee3b3c2e8bd3854ad678376b2573ada134438a47c0c3eec79842be4e8c23f478"
    }
  },
  components: {},
  methods: {}
};
</script>

<style lang="scss" scoped>
</style>
