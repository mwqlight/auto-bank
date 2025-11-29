package com.bank.service.impl;

import com.bank.dto.response.TransactionResponse;
import com.bank.entity.Account;
import com.bank.entity.Transaction;
import com.bank.repository.AccountRepository;
import com.bank.repository.TransactionRepository;
import com.bank.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {
    
    @Autowired
    private TransactionRepository transactionRepository;
    
    @Autowired
    private AccountRepository accountRepository;
    
    @Override
    @Transactional
    public TransactionResponse transfer(Long fromAccountId, Long toAccountId, BigDecimal amount) {
        // 检查账户是否存在
        Account fromAccount = accountRepository.findById(fromAccountId)
                .orElseThrow(() -> new RuntimeException("转出账户不存在"));
        
        Account toAccount = accountRepository.findById(toAccountId)
                .orElseThrow(() -> new RuntimeException("转入账户不存在"));
        
        // 检查金额有效性
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("转账金额必须大于0");
        }
        
        // 检查余额是否充足
        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("转出账户余额不足");
        }
        
        // 执行转账操作
        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));
        
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
        
        // 记录交易
        Transaction transaction = new Transaction();
        transaction.setTransactionNumber(generateTransactionNumber());
        transaction.setFromAccountId(fromAccountId);
        transaction.setToAccountId(toAccountId);
        transaction.setAmount(amount);
        transaction.setTransactionType(0); // 0-转账
        transaction.setStatus(1); // 1-成功
        transaction.setTransactionTime(LocalDateTime.now());
        
        Transaction savedTransaction = transactionRepository.save(transaction);
        
        return convertToResponse(savedTransaction);
    }
    
    @Override
    public List<TransactionResponse> getTransactionsByAccountId(Long accountId) {
        List<Transaction> fromTransactions = transactionRepository.findByFromAccountIdOrderByTransactionTimeDesc(accountId);
        List<Transaction> toTransactions = transactionRepository.findByToAccountIdOrderByTransactionTimeDesc(accountId);
        
        // 合并两个列表并按时间排序
        fromTransactions.addAll(toTransactions);
        fromTransactions.sort((t1, t2) -> t2.getTransactionTime().compareTo(t1.getTransactionTime()));
        
        return fromTransactions.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public TransactionResponse getTransactionById(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("交易记录不存在"));
        return convertToResponse(transaction);
    }
    
    private TransactionResponse convertToResponse(Transaction transaction) {
        TransactionResponse response = new TransactionResponse();
        response.setId(transaction.getId());
        response.setTransactionNumber(transaction.getTransactionNumber());
        response.setFromAccountId(transaction.getFromAccountId());
        response.setToAccountId(transaction.getToAccountId());
        response.setAmount(transaction.getAmount());
        response.setCurrency(transaction.getCurrency());
        response.setTransactionType(transaction.getTransactionType());
        response.setStatus(transaction.getStatus());
        response.setDescription(transaction.getDescription());
        response.setTransactionTime(transaction.getTransactionTime());
        response.setCreatedAt(transaction.getCreatedAt());
        return response;
    }
    
    private String generateTransactionNumber() {
        return "TXN" + LocalDateTime.now().toString().replace("-", "")
                .replace(":", "").replace(".", "").substring(0, 14) 
                + UUID.randomUUID().toString().replace("-", "").substring(0, 6).toUpperCase();
    }
}