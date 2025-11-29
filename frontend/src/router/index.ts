import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import Layout from '@/components/layout/Layout.vue'
import Dashboard from '@/views/dashboard/Index.vue'
import CustomerList from '@/views/customer/List.vue'
import AccountList from '@/views/account/List.vue'
import TransactionList from '@/views/transaction/List.vue'
import Login from '@/views/login/Index.vue'
import Register from '@/views/login/Register.vue'
import { useUserStore } from '@/store/user'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/register',
    name: 'Register',
    component: Register
  },
  {
    path: '/',
    component: Layout,
    children: [
      {
        path: '',
        name: 'Dashboard',
        component: Dashboard
      },
      {
        path: '/customers',
        name: 'Customers',
        component: CustomerList
      },
      {
        path: '/accounts',
        name: 'Accounts',
        component: AccountList
      },
      {
        path: '/transactions',
        name: 'Transactions',
        component: TransactionList
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 添加路由守卫
router.beforeEach((to, from, next) => {
  const userStore = useUserStore()
  
  // 如果访问登录页或注册页且已登录，则重定向到首页
  if ((to.name === 'Login' || to.name === 'Register') && userStore.isLoggedIn()) {
    next({ name: 'Dashboard' })
    return
  }
  
  // 如果访问其他页面且未登录，则重定向到登录页
  if (to.name !== 'Login' && to.name !== 'Register' && !userStore.isLoggedIn()) {
    next({ name: 'Login' })
    return
  }
  
  next()
})

export default router