package com.tarun.walletService.service;

import com.tarun.walletService.entity.WalletTransaction;

import java.math.BigDecimal;
import java.util.List;

public interface WalletTransactionService {
    BigDecimal getBalance(Long userId);
    List<WalletTransaction> getTransactions(Long userId);
}
