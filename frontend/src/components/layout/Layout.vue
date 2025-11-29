<template>
  <el-container class="layout-container">
    <el-aside width="auto">
      <Sidebar />
    </el-aside>
    <el-container>
      <el-header>
        <Header />
      </el-header>
      <el-main>
        <router-view />
      </el-main>
      <el-footer>
        <Footer />
      </el-footer>
    </el-container>
  </el-container>
</template>

<script setup lang="ts">
import Sidebar from './Sidebar.vue'
import Header from './Header.vue'
import Footer from './Footer.vue'
</script>

<style scoped>
.layout-container {
  height: 100vh;
}

.el-header {
  padding: 0;
  box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);
  z-index: 9;
}

.el-main {
  padding: 20px;
  background-color: #f5f5f5;
}

.el-footer {
  padding: 0;
}
</style>