<template>
  <div class="customer-management">
    <el-tabs v-model="activeTab" @tab-change="handleTabChange">
      <el-tab-pane label="客户列表" name="list">
        <CustomerList />
      </el-tab-pane>
      <el-tab-pane label="客户详情" name="detail" disabled>
        <CustomerDetail />
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import CustomerList from './List.vue'
import CustomerDetail from './Detail.vue'

const activeTab = ref('list')

const handleTabChange = (tabName: string) => {
  // 可以在这里添加切换标签页的逻辑
  console.log('切换到标签页:', tabName)
}
</script>

<style scoped>
.customer-management {
  padding: 20px;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}
</style>