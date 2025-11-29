package com.bank.controller;

import com.bank.dto.request.CustomerCreateRequest;
import com.bank.dto.response.ApiResponse;
import com.bank.dto.response.CustomerResponse;
import com.bank.service.CustomerService;
import com.bank.util.ResponseUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    
    @Autowired
    private CustomerService customerService;
    
    @PostMapping
    public ResponseEntity<ApiResponse<CustomerResponse>> createCustomer(
            @Valid @RequestBody CustomerCreateRequest request) {
        CustomerResponse response = customerService.createCustomer(request);
        return ResponseEntity.ok(ResponseUtil.success("客户创建成功", response));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerResponse>> getCustomerById(@PathVariable Long id) {
        CustomerResponse response = customerService.getCustomerById(id);
        return ResponseEntity.ok(ResponseUtil.success(response));
    }
    
    @GetMapping("/number/{customerNumber}")
    public ResponseEntity<ApiResponse<CustomerResponse>> getCustomerByCustomerNumber(
            @PathVariable String customerNumber) {
        CustomerResponse response = customerService.getCustomerByCustomerNumber(customerNumber);
        return ResponseEntity.ok(ResponseUtil.success(response));
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<CustomerResponse>>> getAllCustomers() {
        List<CustomerResponse> response = customerService.getAllCustomers();
        return ResponseEntity.ok(ResponseUtil.success(response));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok(ResponseUtil.success("客户删除成功", null));
    }
}