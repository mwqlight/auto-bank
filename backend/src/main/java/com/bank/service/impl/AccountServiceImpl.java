package com.bank.service.impl;

import com.bank.dto.response.AccountResponse;
import com.bank.entity.Account;
import com.bank.repository.AccountRepository;
import com.bank.repository.CustomerRepository;
import com.bank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private CustomerRepository customerRepository;
    
    @Override
    public AccountResponse createAccount(Long customerId, Integer accountType) {
        // 检查客户是否存在
        customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("客户不存在"));
        
        Account account = new Account();
        account.setAccountNumber(generateAccountNumber());
        account.setCustomerId(customerId);
        account.setAccountType(accountType);
        account.setBalance(BigDecimal.ZERO);
        
        Account savedAccount = accountRepository.save(account);
        
        return convertToResponse(savedAccount);
    }
    
    @Override
    public AccountResponse getAccountById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("账户不存在"));
        return convertToResponse(account);
    }
    
    @Override
    public AccountResponse getAccountByAccountNumber(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("账户不存在"));
        return convertToResponse(account);
    }
    
    @Override
    public List<AccountResponse> getAccountsByCustomerId(Long customerId) {
        return accountRepository.findByCustomerId(customerId).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public AccountResponse deposit(Long accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("账户不存在"));
        
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("存款金额必须大于0");
        }
        
        account.setBalance(account.getBalance().add(amount));
        Account updatedAccount = accountRepository.save(account);
        
        return convertToResponse(updatedAccount);
    }
    
    @Override
    public AccountResponse withdraw(Long accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("账户不存在"));
        
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("取款金额必须大于0");
        }
        
        if (account.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("账户余额不足");
        }
        
        account.setBalance(account.getBalance().subtract(amount));
        Account updatedAccount = accountRepository.save(account);
        
        return convertToResponse(updatedAccount);
    }
    
    @Override
    public void deleteAccount(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("账户不存在"));
        accountRepository.delete(account);
    }
    
    private AccountResponse convertToResponse(Account account) {
        AccountResponse response = new AccountResponse();
        response.setId(account.getId());
        response.setAccountNumber(account.getAccountNumber());
        response.setCustomerId(account.getCustomerId());
        response.setAccountType(account.getAccountType());
        response.setBalance(account.getBalance());
        response.setCurrency(account.getCurrency());
        response.setStatus(account.getStatus());
        response.setCreatedAt(account.getCreatedAt());
        return response;
    }
    
    private String generateAccountNumber() {
        return "ACC" + UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase();
    }
}