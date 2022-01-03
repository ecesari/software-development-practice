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
                <div
                  v-if="!isOwnProfile && !alreadyFollowing"
                  class="card-profile-actions py-4 mt-lg-0"
                >
                  <base-button
                    v-on:click="FollowUser()"
                    type="info"
                    size="sm"
                    class="mr-4"
                    >Follow</base-button
                  >
                  <!-- <base-button type="default" size="sm" class="float-right"
                    >Message</base-button
                  > -->
                </div>
                <div
                  v-if="!isOwnProfile && alreadyFollowing"
                  class="card-profile-actions py-4 mt-lg-0"
                >
                  <base-button type="success" size="sm" class="mr-4"
                    >Already Following</base-button
                  >
                  <!-- <base-button type="default" size="sm" class="float-right"
                    >Message</base-button
                  > -->
                </div>
              </div>
              <div class="col-lg-4 order-lg-1">
                <div class="card-profile-stats d-flex justify-content-center">
                  <div>
                    <span class="heading">{{ userData.following.length }}</span>
                    <span class="description">Following</span>
                  </div>
                  <div>
                    <span class="heading">{{
                      userData.followedBy.length
                    }}</span>
                    <span class="description">Followers</span>
                  </div>
                  <div v-if="isOwnProfile">
                    <span class="heading">{{ userData.balance }}</span>
                    <span class="description">Credits</span>
                  </div>
                  <div v-if="isOwnProfile">
                    <span class="heading">{{ userData.balanceOnHold }}</span>
                    <span class="description">Credits on Hold</span>
                  </div>
                </div>
              </div>
            </div>
            <div class="text-center mt-5">
              <h3>
                {{ userData.username }}
                <span class="font-weight-light"></span>
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
                     <div>
                  <badge
                    v-for="(tag, index) in userData.tags"
                    :key="index"
                    v-bind:type="GetClass(index)"
                    rounded
                    >{{ tag.name }}</badge
                  >
                </div>
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
        balance: 0,
        balanceOnHold: 0,
        following: [],
        followedBy: [],
        tags: []
      },
      isOwnProfile: this.$route.params.userId == null,
      alreadyFollowing: false,
    };
  },
  mounted() {
    this.GetProfile();
    this.AlreadyFollowing();
  },
  methods: {
    GetProfile() {
      var id = this.$route.params.userId;
      apiRegister.GetProfile(id).then((r) => {
        this.userData.username = r.username;
        this.userData.email = r.email;
        this.userData.bio = r.bio;
        this.userData.balance = r.balance;
        this.userData.balanceOnHold = r.balanceOnHold;
        this.userData.following = r.following;
        this.userData.followedBy = r.followedBy;
        debugger;
        this.userData.tags = r.tags;
        console.log("ok.");
      });
    },
    FollowUser() {
      var id = this.$route.params.userId;
      apiRegister.FollowUser(id).then((r) => {
        this.AlreadyFollowing();
        console.log("ok.");
      });
    },
    AlreadyFollowing() {
      var id = this.$route.params.userId;
      apiRegister.CheckIfFollowExists(id).then((r) => {
        this.alreadyFollowing = r;
        console.log("ok.");
      });
    },
    GetClass(index) {
      var i = index + (1 % 3);
      if (i == 1) {
        return "primary";
      } else if (i == 2) {
        return "success";
      } else {
        return "warning";
      }
    },
  },
  props: {
    userId: String,
  },
};
</script>
<style>
</style>
