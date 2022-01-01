<template>
  <div>
    <h1>Your Coordinates:</h1>
    <p>{{ coordinates.lat }} Latitude, {{ coordinates.lng }} Longitude</p>
<GmapMap
  :center="{lat:10, lng:10}"
  :zoom="7"
  map-type-id="terrain"
  style="width: 500px; height: 300px"
>
  <!-- <GmapMarker
    :key="index"
    v-for="(m, index) in markers"
    :position="m.position"
    :clickable="true"
    :draggable="true"
    @click="center=m.position"
  /> -->
</GmapMap>
<span>foo</span>

  
  </div>
</template>

<script>
// import * as VueGoogleMaps from "vue2-google-maps";
import Vue from 'vue'
import * as VueGoogleMaps from 'vue2-google-maps'
 
Vue.use(VueGoogleMaps, {
  load: {
    key: process.env.VUE_APP_GOOGLE_MAP_KEY,
    libraries: 'places', // This is required if you use the Autocomplete plugin
    // OR: libraries: 'places,drawing'
    // OR: libraries: 'places,drawing,visualization'
    // (as you require)
 
    //// If you want to set the version, you can do so:
    // v: '3.26',
  },
 
  //// If you intend to programmatically custom event listener code
  //// (e.g. `this.$refs.gmap.$on('zoom_changed', someFunc)`)
  //// instead of going through Vue templates (e.g. `<GmapMap @zoom_changed="someFunc">`)
  //// you might need to turn this on.
  // autobindAllEvents: false,
 
  //// If you want to manually install components, e.g.
  //// import {GmapMarker} from 'vue2-google-maps/src/components/marker'
  //// Vue.component('GmapMarker', GmapMarker)
  //// then disable the following:
  // installComponents: true,
})

export default {
  
  name: "MyMap",
  components: {
    VueGoogleMaps
  },
  data() {
    return {
      coordinates: {
        lat: 0,
        lng: 0,
      }
    };
  },
  created() {
    //get coordinate from browser
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(this.ShowPosition);
    }

  },
  methods: {
    ShowPosition(position) {
      this.coordinates.lat = position.coords.latitude;
      this.coordinates.lng = position.coords.longitude;
    },
  },
};
</script>
