<template>
  <div>
    <navbar></navbar>
    <loading :loading="isLoading" />
    <div class="mt-5">
      <router-view />
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import Navbar from '../components/Navbar.vue';

export default {
  components: { Navbar },
  name: "Index",
  computed: {
    ...mapGetters("loading", [ "isLoading" ]),
  },
}
</script>