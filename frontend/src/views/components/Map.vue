<template>
  <div>
    <h1>Your Coordinates:</h1>
    <p>{{ coordinates.lat }} Latitude, {{ coordinates.lng }} Longitude</p>
  </div>
</template>

<script>

export default {
  
  name: "map",
  components: {
    
  },
  data() {
    return {
      coordinates: {
        lat: 0,
        lng: 0,
      },
    };
  },
  created() {
    //get coordinate from browser
    debugger;
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
