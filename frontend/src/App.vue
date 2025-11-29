<template>
  <div id="app">
    <Layout>
      <router-view />
    </Layout>
  </div>
</template>

<script setup lang="ts">
import Layout from '@/components/layout/Layout.vue'
// This is the root component of the application
</script>

<style>
#app {
  font-family: 'HarmonyOS Sans', Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #333;
  min-height: 100vh;
}
</style>