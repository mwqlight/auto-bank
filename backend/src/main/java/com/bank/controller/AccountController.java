package com.bank.controller;

import com.bank.dto.response.AccountResponse;
import com.bank.dto.response.ApiResponse;
import com.bank.service.AccountService;
import com.bank.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    
    @Autowired
    private AccountService accountService;
    
    @PostMapping
    public ResponseEntity<ApiResponse<AccountResponse>> createAccount(
            @RequestParam Long customerId,
            @RequestParam Integer accountType) {
        AccountResponse response = accountService.createAccount(customerId, accountType);
        return ResponseEntity.ok(ResponseUtil.success("账户创建成功", response));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AccountResponse>> getAccountById(@PathVariable Long id) {
        AccountResponse response = accountService.getAccountById(id);
        return ResponseEntity.ok(ResponseUtil.success(response));
    }
    
    @GetMapping("/number/{accountNumber}")
    public ResponseEntity<ApiResponse<AccountResponse>> getAccountByAccountNumber(
            @PathVariable String accountNumber) {
        AccountResponse response = accountService.getAccountByAccountNumber(accountNumber);
        return ResponseEntity.ok(ResponseUtil.success(response));
    }
    
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<ApiResponse<List<AccountResponse>>> getAccountsByCustomerId(
            @PathVariable Long customerId) {
        List<AccountResponse> response = accountService.getAccountsByCustomerId(customerId);
        return ResponseEntity.ok(ResponseUtil.success(response));
    }
    
    @PostMapping("/{id}/deposit")
    public ResponseEntity<ApiResponse<AccountResponse>> deposit(
            @PathVariable Long id,
            @RequestParam BigDecimal amount) {
        AccountResponse response = accountService.deposit(id, amount);
        return ResponseEntity.ok(ResponseUtil.success("存款成功", response));
    }
    
    @PostMapping("/{id}/withdraw")
    public ResponseEntity<ApiResponse<AccountResponse>> withdraw(
            @PathVariable Long id,
            @RequestParam BigDecimal amount) {
        AccountResponse response = accountService.withdraw(id, amount);
        return ResponseEntity.ok(ResponseUtil.success("取款成功", response));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.ok(ResponseUtil.success("账户删除成功", null));
    }
}