export interface TransactionResponse {
  id: number
  transactionNumber: string
  fromAccountId: number
  toAccountId: number
  amount: number
  currency: string
  transactionType: number
  status: number
  description?: string
  transactionTime: string
  createdAt: string
}