package com.bank.service;

import com.bank.dto.response.TransactionResponse;
import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {
    TransactionResponse transfer(Long fromAccountId, Long toAccountId, BigDecimal amount);
    List<TransactionResponse> getTransactionsByAccountId(Long accountId);
    TransactionResponse getTransactionById(Long id);
}