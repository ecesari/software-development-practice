<template>
  <header class="header-global">
    <base-nav class="navbar-main" transparent type="" effect="light" expand>
      <router-link slot="brand" class="navbar-brand mr-lg-5" to="/">
        <img src="img/brand/white.png" alt="logo" />
      </router-link>

      <div class="row" slot="content-header" slot-scope="{ closeMenu }">
        <div class="col-6 collapse-brand">
          <a
            href="https://demos.creative-tim.com/vue-argon-design-system/documentation/"
          >
            <img src="img/brand/blue.png" />
          </a>
        </div>
        <div class="col-6 collapse-close">
          <close-button @click="closeMenu"></close-button>
        </div>
      </div>

      <ul class="navbar-nav navbar-nav-hover align-items-lg-center">
        <base-dropdown class="nav-item" menu-classes="dropdown-menu-xl">
          <a
            slot="title"
            href="#"
            class="nav-link"
            data-toggle="dropdown"
            role="button"
          >
            <i class="ni ni-ui-04 d-lg-none"></i>
            <span class="nav-link-inner--text">Components</span>
          </a>
          <div class="dropdown-menu-inner">
            <a
              href="https://demos.creative-tim.com/vue-argon-design-system/documentation/"
              class="media d-flex align-items-center"
            >
              <div
                class="
                  icon icon-shape
                  bg-gradient-primary
                  rounded-circle
                  text-white
                "
              >
                <i class="ni ni-spaceship"></i>
              </div>
              <div class="media-body ml-3">
                <h6 class="heading text-primary mb-md-1">Getting started</h6>
                <p class="description d-none d-md-inline-block mb-0">
                  Get started with Bootstrap, the world's most popular framework
                  for building responsive sites.
                </p>
              </div>
            </a>
            <a
              href="https://demos.creative-tim.com/vue-argon-design-system/documentation/"
              class="media d-flex align-items-center"
            >
              <div
                class="
                  icon icon-shape
                  bg-gradient-warning
                  rounded-circle
                  text-white
                "
              >
                <i class="ni ni-ui-04"></i>
              </div>
              <div class="media-body ml-3">
                <h5 class="heading text-warning mb-md-1">Components</h5>
                <p class="description d-none d-md-inline-block mb-0">
                  Learn how to use Argon compiling Scss, change brand colors and
                  more.
                </p>
              </div>
            </a>
          </div>
        </base-dropdown>
        <base-dropdown tag="li" class="nav-item">
          <a
            slot="title"
            href="#"
            class="nav-link"
            data-toggle="dropdown"
            role="button"
          >
            <i class="ni ni-collection d-lg-none"></i>
            <span class="nav-link-inner--text">Examples</span>
          </a>
          <router-link to="/landing" class="dropdown-item">Landing</router-link>
          <router-link to="/profile" class="dropdown-item">Profile</router-link>
          <router-link to="/login" class="dropdown-item">Login</router-link>
          <router-link to="/register" class="dropdown-item"
            >Register</router-link
          >
        </base-dropdown>
      </ul>
      <ul class="navbar-nav align-items-lg-center ml-lg-auto">
        <li class="nav-item">
          <a
            class="nav-link nav-link-icon"
            href="https://github.com/ecesari/software-development-practice"
            target="_blank"
            rel="noopener"
            data-toggle="tooltip"
            title="Star us on Github"
          >
            <i class="fa fa-github"></i>
            <span class="nav-link-inner--text d-lg-none">Github</span>
          </a>
        </li>

        <li v-if="!userLoggedIn" class="nav-item d-none d-lg-block ml-lg-4">
          <a href="#/register" rel="noopener" class="btn btn-neutral btn-icon">
            <span class="btn-inner--icon">
              <i class="fa fa-user mr-2"></i>
            </span>
            <span class="nav-link-inner--text">Register</span>
          </a>
        </li>

        <li v-if="!userLoggedIn" class="nav-item d-none d-lg-block ml-lg-4">
          <a href="#/login" rel="noopener" class="btn btn-neutral btn-icon">
            <span class="btn-inner--icon">
              <i class="fa fa-magic mr-2"></i>
            </span>
            <span class="nav-link-inner--text">Log In</span>
          </a>
        </li>
        <li v-if="userLoggedIn" class="nav-item d-none d-lg-block ml-lg-4">
          <a href="#/myProfile" rel="noopener" class="btn btn-neutral btn-icon">
            <span class="btn-inner--icon">
              <i class="fa fa-meh mr-2"></i>
            </span>
            <span class="nav-link-inner--text">My Profile</span>
          </a>
        </li>

        <li v-if="userLoggedIn" class="nav-item d-none d-lg-block ml-lg-4">
          <a
            href="#"
            v-on:click="EmptyLocalStorage"
            rel="noopener"
            class="btn btn-neutral btn-icon"
          >
            <span class="btn-inner--icon">
              <i class="fa fa-meh mr-2"></i>
            </span>
            <span class="nav-link-inner--text">Log Out</span>
          </a>
        </li>
      </ul>
    </base-nav>
  </header>
</template>
<script>
import BaseNav from "@/components/BaseNav";
import BaseDropdown from "@/components/BaseDropdown";
import CloseButton from "@/components/CloseButton";

export default {
  data() {
    return {
      userLoggedIn: "",
    };
  },
  mounted() {
    debugger;
    let token = JSON.parse(localStorage.getItem("token"));

    if (token) {
      this.userLoggedIn = true;
    } else {
      this.userLoggedIn = false;
    }
  },
  components: {
    BaseNav,
    CloseButton,
    BaseDropdown,
  },
  methods: {
    EmptyLocalStorage() {
      debugger;
      localStorage.clear();
      document.location.href = '../';

    },
  },
};
</script>
<style>
</style>
