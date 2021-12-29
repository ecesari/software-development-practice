<template>
  <div class="profile-page">
    <section class="section-profile-cover section-shaped my-0">
      <div class="shape shape-style-1 shape-primary shape-skew alpha-4">
        <span></span>
        <span></span>
        <span></span>
        <span></span>
        <span></span>
        <span></span>
        <span></span>
      </div>
    </section>
    <section class="section section-skew">
      <div class="container">
        <card shadow class="card-profile mt--300" no-body>
          <div class="px-4">
            <div class="row justify-content-center">
              <div class="col-lg-3 order-lg-2">
                <!-- <div class="card-profile-image">
                  <a href="#">
                    <img
                      v-lazy="'img/theme/team-4-800x800.jpg'"
                      class="rounded-circle"
                    />
                  </a>
                </div> -->
              </div>
              <div
                class="col-lg-4 order-lg-3 text-lg-right align-self-lg-center"
              >
                <div v-if="!isOwnProfile" class="card-profile-actions py-4 mt-lg-0">
                  <base-button type="info" size="sm" class="mr-4"
                    >Connect</base-button
                  >
                  <base-button type="default" size="sm" class="float-right"
                    >Message</base-button
                  >
                </div>
              </div>
              <div class="col-lg-4 order-lg-1">
                <div class="card-profile-stats d-flex justify-content-center">
                  <div>
                    <span class="heading">22</span>
                    <span class="description">Following</span>
                  </div>
                    <div>
                    <span class="heading">10</span>
                    <span class="description">Followers</span>
                  </div>
                  <!-- <div>
                    <span class="heading">10</span>
                    <span class="description">Photos</span>
                  </div> -->
                  <div v-if="isOwnProfile">
                    <span class="heading">{{userData.balance}}</span>
                    <span class="description">Credits</span>
                  </div>
                </div>
              </div>
            </div>
            <div class="text-center mt-5">
              <h3>
                {{ userData.username }}
                <span class="font-weight-light">, 27</span>
              </h3>
              <!-- <div class="h6 font-weight-300">
                <i class="ni location_pin mr-2"></i>Bucharesst, Romania
              </div> -->
              <div class="h6 mt-4">
                <i class="ni business_briefcase-24 mr-2"></i
                >{{ userData.email }}
              </div>
              <!-- <div>
                <i class="ni education_hat mr-2"></i>University of Computer
                Science
              </div> -->
            </div>
            <div class="mt-5 py-5 border-top text-center">
              <div class="row justify-content-center">
                <div class="col-lg-9">
                  <p>{{ userData.bio }}</p>
                  <!-- <a href="#">Show more</a> -->
                </div>
              </div>
            </div>
          </div>
        </card>
      </div>
    </section>
  </div>
</template>
<script>
import apiRegister from "../api/register";
export default {
  components: {},
  data() {
    return {
      userData: {
        username: "",
        email: "",
        bio: "",
        balance: 0
      },
      isOwnProfile: this.$route.params.userId == null
    };
  },
  mounted() {
    this.GetProfile();
  },
  methods: {
    GetProfile() {
      var id = this.$route.params.userId;
      apiRegister.GetProfile(id).then((r) => {
        this.userData.username = r.username;
        this.userData.email = r.email;
        this.userData.bio = r.bio;
        this.userData.balance = r.balance;
        console.log("ok.");
      });
    },
  },
  props: {
    userId: String,
  },
};
</script>
<style>
</style>
