import { defineStore } from 'pinia'
import { ref } from 'vue'

export interface UserInfo {
  id: number
  username: string
  name: string
}

export const useUserStore = defineStore('user', () => {
  const userInfo = ref<UserInfo | null>(null)
  const token = ref<string | null>(null)

  const setUserInfo = (info: UserInfo) => {
    userInfo.value = info
  }

  const setToken = (newToken: string) => {
    token.value = newToken
  }

  const clearUser = () => {
    userInfo.value = null
    token.value = null
  }

  const isLoggedIn = () => {
    return !!userInfo.value
  }

  return {
    userInfo,
    token,
    setUserInfo,
    setToken,
    clearUser,
    isLoggedIn
  }
})