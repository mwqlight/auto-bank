<template>
  <div class="sidebar">
    <el-menu
      :default-active="activeMenu"
      class="el-menu-vertical-demo"
      :collapse="isCollapse"
      @select="handleSelect"
      background-color="#545c64"
      text-color="#fff"
      active-text-color="#ffd04b"
    >
      <el-menu-item index="/">
        <el-icon><House /></el-icon>
        <template #title>首页</template>
      </el-menu-item>
      <el-sub-menu index="customer">
        <template #title>
          <el-icon><User /></el-icon>
          <span>客户管理</span>
        </template>
        <el-menu-item index="/customers">
          <el-icon><TrendCharts /></el-icon>
          <span>客户列表</span>
        </el-menu-item>
        <el-menu-item index="/customers/create">
          <el-icon><DocumentAdd /></el-icon>
          <span>新增客户</span>
        </el-menu-item>
      </el-sub-menu>
      <el-sub-menu index="account">
        <template #title>
          <el-icon><Wallet /></el-icon>
          <span>账户管理</span>
        </template>
        <el-menu-item index="/accounts">
          <el-icon><TrendCharts /></el-icon>
          <span>账户列表</span>
        </el-menu-item>
        <el-menu-item index="/accounts/create">
          <el-icon><DocumentAdd /></el-icon>
          <span>新开账户</span>
        </el-menu-item>
      </el-sub-menu>
      <el-sub-menu index="transaction">
        <template #title>
          <el-icon><Collection /></el-icon>
          <span>交易管理</span>
        </template>
        <el-menu-item index="/transactions">
          <el-icon><TrendCharts /></el-icon>
          <span>交易记录</span>
        </el-menu-item>
        <el-menu-item index="/transactions/deposit">
          <el-icon><RefreshRight /></el-icon>
          <span>存款</span>
        </el-menu-item>
        <el-menu-item index="/transactions/withdraw">
          <el-icon><RefreshLeft /></el-icon>
          <span>取款</span>
        </el-menu-item>
        <el-menu-item index="/transactions/transfer">
          <el-icon><Swap /></el-icon>
          <span>转账</span>
        </el-menu-item>
      </el-sub-menu>
    </el-menu>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { House, User, Wallet, Collection, DocumentAdd, TrendCharts, RefreshRight, RefreshLeft, Swap } from '@element-plus/icons-vue'

const router = useRouter()
const route = useRoute()

const isCollapse = ref(false)

const activeMenu = computed(() => {
  const { meta, path } = route
  // if set path, the sidebar will highlight the path you set
  if (meta.activeMenu) {
    return meta.activeMenu as string
  }
  return path
})

const handleSelect = (key: string) => {
  router.push(key)
}
</script>

<style scoped>
.sidebar {
  height: 100%;
  border-right: solid 1px #e6e6e6;
}

.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  min-height: 400px;
}

.el-menu {
  height: 100%;
  border-right: none;
}
</style>