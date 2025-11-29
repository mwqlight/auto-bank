package com.bank.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Getter
@Setter
public class Transaction extends BaseEntity {
    
    @Column(name = "transaction_number", unique = true, nullable = false)
    private String transactionNumber;
    
    @Column(name = "from_account_id", nullable = false)
    private Long fromAccountId;
    
    @Column(name = "to_account_id", nullable = false)
    private Long toAccountId;
    
    @Column(name = "amount", precision = 15, scale = 2, nullable = false)
    private BigDecimal amount;
    
    @Column(name = "currency", length = 3)
    private String currency = "CNY";
    
    @Column(name = "transaction_type")
    private Integer transactionType; // 0-转账 1-存款 2-取款 3-支付
    
    @Column(name = "status")
    private Integer status = 0; // 0-处理中 1-成功 2-失败 3-取消
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "transaction_time")
    private LocalDateTime transactionTime;
}