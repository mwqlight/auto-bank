package com.bank.service;

import com.bank.dto.response.AccountResponse;
import java.math.BigDecimal;
import java.util.List;

public interface AccountService {
    AccountResponse createAccount(Long customerId, Integer accountType);
    AccountResponse getAccountById(Long id);
    AccountResponse getAccountByAccountNumber(String accountNumber);
    List<AccountResponse> getAccountsByCustomerId(Long customerId);
    AccountResponse deposit(Long accountId, BigDecimal amount);
    AccountResponse withdraw(Long accountId, BigDecimal amount);
    void deleteAccount(Long id);
}