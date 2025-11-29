package com.bank.service;

import com.bank.dto.request.CustomerCreateRequest;
import com.bank.dto.response.CustomerResponse;
import java.util.List;

public interface CustomerService {
    CustomerResponse createCustomer(CustomerCreateRequest request);
    CustomerResponse getCustomerById(Long id);
    CustomerResponse getCustomerByCustomerNumber(String customerNumber);
    List<CustomerResponse> getAllCustomers();
    void deleteCustomer(Long id);
}