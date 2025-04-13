package com.tarun.walletService.service.impl;

import com.tarun.walletService.entity.Wallet;
import com.tarun.walletService.entity.WalletTransaction;
import com.tarun.walletService.repository.WalletRepository;
import com.tarun.walletService.repository.WalletTransactionRepository;
import com.tarun.walletService.service.WalletTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WalletTransactionServiceImpl implements WalletTransactionService {

    private final WalletRepository walletRepository;
    private final WalletTransactionRepository transactionRepository;

    @Override
    public BigDecimal getBalance(Long userId) {
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));
        return wallet.getBalance();
    }

    @Override
    public List<WalletTransaction> getTransactions(Long userId) {
        return transactionRepository.findAllByUserIdOrderByTimestampDesc(userId);
    }
}
