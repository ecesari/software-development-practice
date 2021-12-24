<template>
    <div>

            <section class="section section-lg section-shaped overflow-hidden my-0">
        <div class="shape shape-style-1 shape-default shape-skew">
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
                  <br> <br>   <h3 class="display-3 text-white">Create a Service
                        <span class="text-white">contribute to socialhub</span>
                    </h3>
                    <p class="lead text-white">You can create services to contribute to the socialhub community. You gain a time balance depending on the duration of the service where you can participate in other socialhub services.</p>
                    <div class="container mb-5">
            <!-- Inputs -->
<br>          
                <div class="col-lg-12 ">
                    <base-input placeholder="Header"></base-input>          
                </div>
                <div class="col-lg-12">
                
                    <base-input placeholder="Search"
                                addon-left-icon="ni ni-zoom-split-in">
                    </base-input>
                </div>
                <!-- <div class="col-lg-4 col-sm-6">
                    <base-input placeholder="Regular" disabled>
                    </base-input>
                    <base-input placeholder="Birthday"
                                addon-right-icon="ni ni-zoom-split-in">
                    </base-input>
                </div>
                <div class="col-lg-4 col-sm-6">
                    <base-input placeholder="Success" :valid="true">
                    </base-input>

                    <base-input placeholder="Success" :valid="false">
                    </base-input>
                </div> -->
         
        </div>
                </div>
                <!-- <div class="col-lg-5 mb-lg-auto">
                    <div class="transform-perspective-right">
                        <card class="border-0" type="secondary" shadow
                              body-classes="px-lg-5 py-lg-5"
                              header-classes="pb-5 bg-white">
                            <template slot="header">
                                <div class="text-muted text-center mb-3">
                                    <small>Sign in with</small>
                                </div>
                                <div class="btn-wrapper text-center">
                                    <base-button type="neutral">
                                        <img slot="icon" src="img/icons/common/github.svg">
                                        Github
                                    </base-button>

                                    <base-button type="neutral">
                                        <img slot="icon" src="img/icons/common/google.svg">
                                        Google
                                    </base-button>
                                </div>
                            </template>
                            <template>
                                <div class="text-center text-muted mb-4">
                                    <small>Or sign in with credentials</small>
                                </div>
                                <form role="form">
                                    <base-input alternative
                                                placeholder="Email"
                                                addon-left-icon="ni ni-email-83">
                                    </base-input>
                                    <base-input alternative
                                                type="password"
                                                placeholder="Password"
                                                addon-left-icon="ni ni-lock-circle-open">
                                    </base-input>
                                    <base-checkbox>
                                        Remember me
                                    </base-checkbox>
                                    <div class="text-center">
                                        <base-button type="primary" class="my-4">Sign In</base-button>
                                    </div>
                                </form>
                            </template>
                        </card>
                    </div>
                </div> -->
            </div>
        </div>
    </section>
        <!-- <inputs></inputs> -->
        <section class="section pb-0 section-components">
        <div class="container mb-5 bg-secondary">
     
        </div>
    </section>
    </div>
</template>
<script>

import Examples from "./components/Examples";

export default {
  components: {   
    Examples
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

      tags: [],
    };
  },
};
</script>
