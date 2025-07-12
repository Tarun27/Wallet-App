package com.tarun.walletTransactionService.service;

import com.tarun.walletTransactionService.entity.WalletTransferTransaction;

import java.math.BigDecimal;

public interface TransactionService {
    void initiateTransfer(Long fromUserId, Long toUserId, BigDecimal amount);
    WalletTransferTransaction getTransactionById(Long id);

}
