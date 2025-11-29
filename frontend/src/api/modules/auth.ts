import apiClient from '../index'

export interface LoginRequest {
  username: string
  password: string
}

export interface RegisterRequest {
  username: string
  password: string
  name: string
}

export interface LoginResponse {
  token: string
  userInfo: {
    id: number
    username: string
    name: string
  }
}

export const authApi = {
  /**
   * 用户登录
   * @param data 登录信息
   */
  async login(data: LoginRequest) {
    try {
      const response = await apiClient.post<LoginResponse>('/auth/login', data)
      return response.data
    } catch (error) {
      throw new Error('用户名或密码错误')
    }
  },
  
  /**
   * 用户注册
   * @param data 注册信息
   */
  async register(data: RegisterRequest) {
    try {
      const response = await apiClient.post('/auth/register', data)
      return response.data
    } catch (error) {
      throw new Error('注册失败')
    }
  },
  
  /**
   * 用户登出
   */
  async logout() {
    try {
      await apiClient.post('/auth/logout')
      return
    } catch (error) {
      throw new Error('登出失败')
    }
  }
}