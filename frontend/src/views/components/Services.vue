<template>
  <div class="container">
    <div class="row justify-content-center">
      <div v-if="this.filter == 'all'" class="row pull-left">
        <base-dropdown>
          <base-button
            slot="title"
            type="warning"
            class="dropdown-toggle float-right"
          >
            Sort By
          </base-button>
          <a class="dropdown-item" href="#" v-on:click="SortBy('distanceAsc')">Distance <span class="btn-inner--icon">
              <i class="fa fa- fa-sort-amount-asc
 mr-2"></i>
            </span></a>
          <a class="dropdown-item" href="#" v-on:click="SortBy('distanceDesc')">Distance <span class="btn-inner--icon">
              <i class="fa fa- fa-sort-amount-desc
 mr-2"></i>
            </span> </a>
          <a class="dropdown-item" href="#" v-on:click="SortBy('serviceDateAsc')"
            >Service Date <span class="btn-inner--icon">
              <i class="fa fa- fa-sort-amount-asc
 mr-2"></i>
            </span></a
          >
              <a class="dropdown-item" href="#" v-on:click="SortBy('serviceDateDesc')"
            >Service Date <span class="btn-inner--icon">
              <i class="fa fa- fa-sort-amount-desc
 mr-2"></i>
            </span></a
          >
          <a class="dropdown-item" href="#" v-on:click="SortBy('createdDateAsc')"
            >Created Date <span class="btn-inner--icon">
              <i class="fa fa- fa-sort-amount-asc
 mr-2"></i>
            </span></a
          >
             <a class="dropdown-item" href="#" v-on:click="SortBy('createdDateDesc')"
            >Created Date <span class="btn-inner--icon">
              <i class="fa fa- fa-sort-amount-desc
 mr-2"></i>
            </span></a
          >
        </base-dropdown>
      </div>

      <div class="col-lg-12 pt-100">
        <div
          v-for="(serviceArray, index) in nestedServiceArray"
          :key="index"
          class="row row-grid"
        >
          <div
            v-for="(service, index) in serviceArray"
            :key="index"
            class="col-lg-4"
          >
            <card class="border-0" hover shadow body-classes="py-5">
              <icon
                v-bind:type="GetClass(index)"
                v-bind:name="GetIcon(index)"
                rounded
                class="mb-4"
              >
              </icon>
              <h6 v-bind:class="GetTextClass(index)">{{ service.header }}</h6>
              <p class="description mt-3">
                {{ service.description }}
              </p>
              <div>
                <badge v-bind:type="GetClass(index)" rounded
                  >{{ service.minutes }} credits</badge
                >
                <badge v-bind:type="GetClass(index)" rounded
                  >{{ service.distanceToUserString }}</badge
                >
              </div>
              <base-button
                tag="a"
                v-if="isLoggedIn"
                :href="'#/service/' + service.id"
                v-bind:type="GetClass(index)"
                class="mt-4"
              >
                Learn more
              </base-button>
              <base-button
                tag="a"
                v-if="!isLoggedIn"
                :href="'#/register/'"
                v-bind:type="GetClass(index)"
                class="mt-4"
              >
                Register to Learn More
              </base-button>
            </card>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Hero from "./Hero";
import apiRegister from "@/api/register";
import BaseDropdown from "@/components/BaseDropdown";

export default {
  name: "services",
  data() {
    return {
      serviceResult: [],
      nestedServiceArray: [],
      getOngoingOnly: false,
      isLoggedIn: false,
    };
  },
  mounted() {
    this.GetServices();
    let token = JSON.parse(localStorage.getItem("token"));

    if (token) {
      this.isLoggedIn = true;
    } else {
      this.isLoggedIn = false;
    }
  },
  methods: {
    GetServices() {
      if (this.filter == "first3") {
        apiRegister.GetAllServicesForHome().then((response) => {
          this.serviceResult = response;
          this.nestedServiceArray = this.SplitList();
        });
      } else {
        apiRegister
          .GetAllServices(this.getOngoingOnly, this.filter)
          .then((response) => {
            this.serviceResult = response;
            this.nestedServiceArray = this.SplitList();
          });
      }
    },
    SplitList() {
      var array = this.serviceResult;
      var listOfArrays = [];

      var i,
        j,
        temporary,
        chunk = 3;
      for (i = 0, j = array.length; i < j; i += chunk) {
        temporary = array.slice(i, i + chunk);
        listOfArrays.push(temporary);
      }
      return listOfArrays;
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
    GetIcon(index) {
      var i = index + (1 % 3);
      if (i == 1) {
        return "ni ni-check-bold";
      } else if (i == 2) {
        return "ni ni-istanbul";
      } else {
        return "ni ni-planet";
      }
    },
    GetTextClass(index) {
      var i = index + (1 % 3);
      if (i == 1) {
        return "text-primary text-uppercase";
      } else if (i == 2) {
        return "text-success text-uppercase";
      } else {
        return "text-warning text-uppercase";
      }
    },
    SortBy(sortBy) {
      apiRegister
        .GetAllServicesSorted(this.getOngoingOnly, this.filter, sortBy)
        .then((response) => {
          this.serviceResult = response;
          this.nestedServiceArray = this.SplitList();
        });
    },
  },
  props: {
    getByUser: Boolean,
    filter: String,
  },
  components: {
    Hero,
    BaseDropdown,
  },
};
</script>
