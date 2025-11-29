package com.bank.dto.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
public class CustomerResponse {
    private Long id;
    private String customerNumber;
    private String name;
    private String idCardNumber;
    private String phone;
    private String email;
    private String address;
    private Integer riskLevel;
    private Integer customerType;
    private Integer status;
    private LocalDateTime createdAt;
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getCustomerNumber() {
        return customerNumber;
    }
    
    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getIdCardNumber() {
        return idCardNumber;
    }
    
    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public Integer getRiskLevel() {
        return riskLevel;
    }
    
    public void setRiskLevel(Integer riskLevel) {
        this.riskLevel = riskLevel;
    }
    
    public Integer getCustomerType() {
        return customerType;
    }
    
    public void setCustomerType(Integer customerType) {
        this.customerType = customerType;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}