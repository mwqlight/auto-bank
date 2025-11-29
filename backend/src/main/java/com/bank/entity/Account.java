package com.bank.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Table(name = "accounts")
public class Account extends BaseEntity {
    
    @Column(name = "account_number", unique = true, nullable = false)
    private String accountNumber;
    
    @Column(name = "customer_id", nullable = false)
    private Long customerId;
    
    @Column(name = "account_type", nullable = false)
    private Integer accountType; // 0-活期 1-定期 2-信用卡 3-投资账户
    
    @Column(name = "balance", precision = 15, scale = 2)
    private BigDecimal balance = BigDecimal.ZERO;
    
    @Column(name = "currency", length = 3)
    private String currency = "CNY";
    
    @Column(name = "status")
    private Integer status = 0; // 0-正常 1-冻结 2-注销
    
    // Getter方法
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public Long getCustomerId() {
        return customerId;
    }
    
    public Integer getAccountType() {
        return accountType;
    }
    
    public BigDecimal getBalance() {
        return balance;
    }
    
    public String getCurrency() {
        return currency;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    // Setter方法
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
    
    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }
    
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
    
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
}