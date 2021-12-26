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
              <div class="col-lg-3 order-lg-2"></div>
              <div
                class="col-lg-4 order-lg-3 text-lg-right align-self-lg-center"
              >
                <div class="card-profile-actions py-4 mt-lg-0">
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
                    <span class="heading">{{
                      serviceData.attendingUserCount
                    }}</span>
                    <span class="description">Participants</span>
                  </div>
                  <div>
                    <span class="heading">{{serviceData.quota}}</span>
                    <span class="description">Quota</span>
                  </div>
                  <!-- <div>
                    <span class="heading">89</span>
                    <span class="description">Comments</span>
                  </div> -->
                </div>
              </div>
            </div>
            <div class="text-center mt-5">
              <h3>
                {{ serviceData.header }}
                <span class="font-weight-light">
                  by {{ serviceData.createdUserName }}</span
                >
              </h3>
              <!-- <div class="h6 font-weight-300"><i class="ni location_pin mr-2"></i>{{ serviceData.quota }}</div> -->
              <div>
                <i class="ni ni-square-pin"></i> : {{ serviceData.location }}
              </div>
              <br />
              <div>
                <i class="ni ni-time-alarm"></i>: {{ serviceData.time }}
              </div>
              <div>
                <i class="ni ni-watch-time"></i>:
                {{ serviceData.minutes }} minutes
              </div>
              <!-- <div>
                <i class="ni ni-single-02"></i>: {{ serviceData.quota }} people
              </div> -->
            </div>
            <div class="mt-2 py-5 border-top text-center">
              <div class="row justify-content-center">
                <div class="col-lg-9">
                  <p>{{ serviceData.description }}</p>
                  <!-- <a href="#">Show more</a> -->

                  <div
                    v-for="(tag, index) in serviceData.serviceTags"
                    :key="index"
                  >
                    <badge v-bind:type="GetClass(index)" rounded>{{
                      tag.name
                    }}</badge>
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
      serviceData: {
        location: "",
        time: "",
        header: "",
        minutes: "",
        description: "",
        quota: "",
        attendingUserCount: "",
        createdUserIdId: "",
        createdUserName: "",
        serviceTags: [],
      },
    };
  },
  mounted() {
    this.GetService();
  },
  methods: {
    GetService() {
      debugger;
      var id = this.$route.params.service_id;
      apiRegister.GetService(id).then((r) => {
        this.serviceData.location = r.location;
        this.serviceData.time = r.time;
        this.serviceData.header = r.header;
        this.serviceData.minutes = r.minutes;
        this.serviceData.description = r.description;
        this.serviceData.quota = r.quota;
        this.serviceData.createdUserIdId = r.createdUserIdId;
        this.serviceData.createdUserName = r.createdUserName;
        this.serviceData.serviceTags = r.serviceTags;
        this.serviceData.attendingUserCount = r.attendingUserCount;
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
};
</script>
<style>
</style>
