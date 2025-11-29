import apiClient from '../index'
import { CustomerCreateRequest, CustomerResponse } from '@/types/customer'

// Customer API endpoints
export const customerApi = {
  // Create a new customer
  createCustomer(data: CustomerCreateRequest) {
    return apiClient.post<CustomerResponse>('/customers', data)
  },
  
  // Get customer by ID
  getCustomerById(id: number) {
    return apiClient.get<CustomerResponse>(`/customers/${id}`)
  },
  
  // Get customer by customer number
  getCustomerByNumber(customerNumber: string) {
    return apiClient.get<CustomerResponse>(`/customers/number/${customerNumber}`)
  },
  
  // Get all customers
  getAllCustomers() {
    return apiClient.get<CustomerResponse[]>('/customers')
  },
  
  // Delete customer
  deleteCustomer(id: number) {
    return apiClient.delete(`/customers/${id}`)
  }
}