package com.bank.controller;

import com.bank.dto.response.ApiResponse;
import com.bank.dto.response.TransactionResponse;
import com.bank.service.TransactionService;
import com.bank.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    
    @Autowired
    private TransactionService transactionService;
    
    @PostMapping("/transfer")
    public ResponseEntity<ApiResponse<TransactionResponse>> transfer(
            @RequestParam Long fromAccountId,
            @RequestParam Long toAccountId,
            @RequestParam BigDecimal amount) {
        try {
            TransactionResponse response = transactionService.transfer(fromAccountId, toAccountId, amount);
            return ResponseEntity.ok(ResponseUtil.success("转账成功", response));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseUtil.error(400, e.getMessage()));
        }
    }
    
    @GetMapping("/account/{accountId}")
    public ResponseEntity<ApiResponse<List<TransactionResponse>>> getTransactionsByAccountId(
            @PathVariable Long accountId) {
        List<TransactionResponse> transactions = transactionService.getTransactionsByAccountId(accountId);
        return ResponseEntity.ok(ResponseUtil.success("交易记录获取成功", transactions));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TransactionResponse>> getTransactionById(@PathVariable Long id) {
        TransactionResponse transaction = transactionService.getTransactionById(id);
        return ResponseEntity.ok(ResponseUtil.success("交易详情获取成功", transaction));
    }
}