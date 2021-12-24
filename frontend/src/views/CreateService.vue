<template>
  <div>
    <section class="section section-lg section-shaped overflow-hidden my-0">
      <div class="shape shape-style-3 shape-default shape-skew">
        <span></span>
        <span></span>
        <span></span>
        <span></span>
        <span></span>
        <span></span>
        <span></span>
        <span></span>
      </div>
      <div class="container shape-container py-0 pb-5">
        <div class="row row-grid justify-content-between align-items-center">
          <div class="col-lg-12">
            <br />
            <br />
            <h3 class="display-3 text-white">
              Create a Service
              <span class="text-white">contribute to socialhub</span>
            </h3>
            <p class="lead text-white">
              You can create services to contribute to the socialhub community.
              You gain a time balance depending on the duration of the service
              where you can participate in other socialhub services.
            </p>
            <div class="container mb-5">
              <!-- Inputs -->
              <form role="form">
                <br />
                <div class="col-lg-12">
                  <base-input
                    placeholder="Header"
                    addon-left-icon="ni ni-align-left-2"
                    v-model="serviceInputs.header"
                  ></base-input>
                </div>
                <div class="col-lg-12">
                  <textarea
                    class="form-control form-control-alternative"
                    v-model="serviceInputs.description"
                    rows="3"
                    placeholder="Write a description of the service here ..."
                  ></textarea>
                  <br />
                </div>
                <div class="col-lg-12">
                  <base-input
                    placeholder="Location"
                    addon-left-icon="ni ni-pin-3"
                    v-model="serviceInputs.location"
                  ></base-input>
                </div>
                <div class="col-lg-12">
                  <date-picker
                    input-class="form-control"
                    v-model="serviceInputs.time"
                    type="datetime"
                  ></date-picker>
                </div>
                <br />
                <div class="col-lg-12">
                  <input
                    type="number"
                    class="form-control"
                    v-model="serviceInputs.quota"
                    placeholder="Quota"
                  />
                </div>
                <br>
                <div class="col-lg-12">
                  <input
                    type="number"
                    class="form-control"
                    v-model="serviceInputs.minutes"
                    placeholder="Duration (in minutes)"
                  />
                </div>
                <div class="text-center">
                  <base-button
                    v-on:click="SendService"
                    type="success"
                    class="my-4"
                    >Create service</base-button
                  >
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </section>
    <!-- <inputs></inputs> -->
    <section class="section pb-0 section-components">
      <div class="container mb-5 bg-secondary"></div>
    </section>
  </div>
</template>
<script>
import apiRegister from "../api/register";
import DatePicker from "vue2-datepicker";

export default {
  components: {
    DatePicker,
  },
  data() {
    return {
      serviceInputs: {
        location: "",
        time: "",
        header: "",
        minutes: "",
        description: "",
        quota: "",
        createdUserIdId: "",
        // selectedTags: [],
      },
    };
  },
  methods:
  {
      SendService() {
      console.log("Send service started")
      apiRegister.CreateService(this.serviceInputs).then((r) => {
            console.log("Send service ok")
              document.location.href = '../Service/' + r;
      });
    }
  }
};
</script>
