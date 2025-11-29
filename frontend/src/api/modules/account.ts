import apiClient from '../index'
import { AccountResponse } from '@/types/account'

// Account API endpoints
export const accountApi = {
  // Create a new account
  createAccount(customerId: number, accountType: number) {
    return apiClient.post<AccountResponse>('/accounts', null, {
      params: { customerId, accountType }
    })
  },
  
  // Get account by ID
  getAccountById(id: number) {
    return apiClient.get<AccountResponse>(`/accounts/${id}`)
  },
  
  // Get account by account number
  getAccountByNumber(accountNumber: string) {
    return apiClient.get<AccountResponse>(`/accounts/number/${accountNumber}`)
  },
  
  // Get accounts by customer ID
  getAccountsByCustomerId(customerId: number) {
    return apiClient.get<AccountResponse[]>(`/accounts/customer/${customerId}`)
  },
  
  // Get all accounts
  getAllAccounts() {
    return apiClient.get<AccountResponse[]>('/accounts')
  },
  
  // Deposit money
  deposit(accountId: number, amount: number) {
    return apiClient.post<AccountResponse>(`/accounts/${accountId}/deposit`, null, {
      params: { amount }
    })
  },
  
  // Withdraw money
  withdraw(accountId: number, amount: number) {
    return apiClient.post<AccountResponse>(`/accounts/${accountId}/withdraw`, null, {
      params: { amount }
    })
  },
  
  // Delete account
  deleteAccount(id: number) {
    return apiClient.delete(`/accounts/${id}`)
  }
}