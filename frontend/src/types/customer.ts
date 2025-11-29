export interface CustomerCreateRequest {
  name: string
  idCardNumber: string
  phone: string
  email?: string
  address?: string
}

export interface CustomerResponse {
  id: number
  customerNumber: string
  name: string
  phone: string
  email?: string
  riskLevel: number
  customerType: number
  status: number
  createdAt: string
}