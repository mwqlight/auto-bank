package com.bank.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "customers")
public class Customer extends BaseEntity {
    
    @Column(name = "customer_number", unique = true, nullable = false)
    private String customerNumber;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "id_card_number", unique = true, nullable = false)
    private String idCardNumber;
    
    @Column(name = "phone", unique = true, nullable = false)
    private String phone;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "address")
    private String address;
    
    @Column(name = "risk_level")
    private Integer riskLevel = 0; // 风险等级 0-低 1-中 2-高
    
    @Column(name = "customer_type")
    private Integer customerType = 0; // 0-个人 1-企业
    
    @Column(name = "status")
    private Integer status = 0; // 0-正常 1-冻结 2-注销
    
    // 手动添加所有getter和setter方法
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
}