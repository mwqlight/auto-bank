<template>
  <div class="account-management">
    <el-tabs v-model="activeTab" @tab-change="handleTabChange">
      <el-tab-pane label="账户列表" name="list">
        <AccountList />
      </el-tab-pane>
      <el-tab-pane label="账户详情" name="detail" disabled>
        <AccountDetail />
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import AccountList from './List.vue'
import AccountDetail from './Detail.vue'

const activeTab = ref('list')

const handleTabChange = (tabName: string) => {
  // 可以在这里添加切换标签页的逻辑
  console.log('切换到标签页:', tabName)
}
</script>

<style scoped>
.account-management {
  padding: 20px;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}
</style>