package com.bank.service.impl;

import com.bank.dto.request.CustomerCreateRequest;
import com.bank.dto.response.CustomerResponse;
import com.bank.entity.Customer;
import com.bank.repository.CustomerRepository;
import com.bank.service.CustomerService;
import com.bank.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    
    @Autowired
    private CustomerRepository customerRepository;
    
    @Override
    public CustomerResponse createCustomer(CustomerCreateRequest request) {
        // 检查是否已存在相同身份证号或手机号的客户
        if (customerRepository.findByIdCardNumber(request.getIdCardNumber()).isPresent()) {
            throw new RuntimeException("该身份证号已注册");
        }
        
        if (customerRepository.findByPhone(request.getPhone()).isPresent()) {
            throw new RuntimeException("该手机号已注册");
        }
        
        Customer customer = new Customer();
        customer.setCustomerNumber(generateCustomerNumber());
        customer.setName(request.getName());
        customer.setIdCardNumber(request.getIdCardNumber());
        customer.setPhone(request.getPhone());
        customer.setEmail(request.getEmail());
        customer.setAddress(request.getAddress());
        
        Customer savedCustomer = customerRepository.save(customer);
        
        return convertToResponse(savedCustomer);
    }
    
    @Override
    public CustomerResponse getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("客户不存在"));
        return convertToResponse(customer);
    }
    
    @Override
    public CustomerResponse getCustomerByCustomerNumber(String customerNumber) {
        Customer customer = customerRepository.findByCustomerNumber(customerNumber)
                .orElseThrow(() -> new RuntimeException("客户不存在"));
        return convertToResponse(customer);
    }
    
    @Override
    public List<CustomerResponse> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("客户不存在"));
        customer.setDeleted(true);
        customerRepository.save(customer);
    }
    
    private CustomerResponse convertToResponse(Customer customer) {
        CustomerResponse response = new CustomerResponse();
        response.setId(customer.getId());
        response.setCustomerNumber(customer.getCustomerNumber());
        response.setName(customer.getName());
        response.setPhone(customer.getPhone());
        response.setEmail(customer.getEmail());
        response.setRiskLevel(customer.getRiskLevel());
        response.setCustomerType(customer.getCustomerType());
        response.setStatus(customer.getStatus());
        response.setCreatedAt(customer.getCreatedAt());
        return response;
    }
    
    private String generateCustomerNumber() {
        return "CUST" + UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase();
    }
}