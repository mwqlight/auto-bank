import apiClient from '../index'
import { TransactionResponse } from '@/types/transaction'

// Transaction API endpoints
export const transactionApi = {
  // Transfer money between accounts
  transfer(fromAccountId: number, toAccountId: number, amount: number) {
    return apiClient.post<TransactionResponse>('/transactions/transfer', null, {
      params: { fromAccountId, toAccountId, amount }
    })
  },
  
  // Get transactions by account ID
  getTransactionsByAccountId(accountId: number) {
    return apiClient.get<TransactionResponse[]>(`/transactions/account/${accountId}`)
  },
  
  // Get transaction by ID
  getTransactionById(id: number) {
    return apiClient.get<TransactionResponse>(`/transactions/${id}`)
  }
}