package com.tarun.walletService.repository;

import com.tarun.walletService.entity.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, Long> {
    List<WalletTransaction> findAllByUserIdOrderByTimestampDesc(Long userId);
}
