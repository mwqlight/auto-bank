<template>
  <div class="header">
    <el-page-header @back="goBack" :content="pageTitle" v-if="showBack" />
    <h2 v-else>{{ pageTitle }}</h2>
    <div class="header-actions">
      <el-dropdown @command="handleCommand">
        <span class="el-dropdown-link">
          <el-avatar :size="32" icon="UserFilled" />
          <span class="username">{{ userInfo.name }}</span>
          <el-icon class="el-icon--right">
            <arrow-down />
          </el-icon>
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item command="profile">个人信息</el-dropdown-item>
            <el-dropdown-item command="settings">设置</el-dropdown-item>
            <el-dropdown-item divided command="logout">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useMainStore } from '@/store'
import { ArrowDown, UserFilled } from '@element-plus/icons-vue'

const router = useRouter()
const route = useRoute()
const store = useMainStore()

const pageTitle = computed(() => {
  const titles: Record<string, string> = {
    '/dashboard': '银行管理系统',
    '/customers': '客户管理',
    '/accounts': '账户管理',
    '/transactions': '交易记录'
  }
  return titles[route.path] || '银行管理系统'
})

const showBack = computed(() => {
  return route.meta.showBack === true
})

const userInfo = computed(() => {
  return store.userInfo || { name: '管理员' }
})

const goBack = () => {
  router.back()
}

const handleCommand = (command: string) => {
  switch (command) {
    case 'profile':
      // Navigate to profile page
      router.push('/profile')
      break
    case 'settings':
      // Navigate to settings page
      router.push('/settings')
      break
    case 'logout':
      // Handle logout
      store.setUserInfo(null)
      router.push('/login')
      break
  }
}
</script>

<style scoped>
.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
  height: 60px;
  border-bottom: 1px solid #dcdfe6;
  background-color: #fff;
}

.header-actions {
  display: flex;
  align-items: center;
}

.username {
  margin-left: 10px;
  font-size: 14px;
}

.el-dropdown-link {
  cursor: pointer;
  display: flex;
  align-items: center;
}
</style>