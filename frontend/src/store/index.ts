import { defineStore } from 'pinia'

export const useMainStore = defineStore('main', {
  state: () => ({
    isLoading: false,
    userInfo: null as any,
    notification: {
      show: false,
      message: '',
      type: 'success' as 'success' | 'error' | 'warning' | 'info'
    }
  }),
  
  getters: {
    isAuthenticated: (state) => !!state.userInfo
  },
  
  actions: {
    setLoading(loading: boolean) {
      this.isLoading = loading
    },
    
    setUserInfo(userInfo: any) {
      this.userInfo = userInfo
    },
    
    showNotification(message: string, type: 'success' | 'error' | 'warning' | 'info' = 'success') {
      this.notification = {
        show: true,
        message,
        type
      }
      
      // Auto hide after 3 seconds
      setTimeout(() => {
        this.notification.show = false
      }, 3000)
    }
  }
})