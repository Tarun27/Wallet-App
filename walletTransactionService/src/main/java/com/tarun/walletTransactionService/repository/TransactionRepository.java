package com.tarun.walletTransactionService.repository;

import com.tarun.walletTransactionService.entity.WalletTransferTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<WalletTransferTransaction, Long> {

    List<WalletTransferTransaction> findAllByFromUserIdOrToUserIdOrderByCreatedAtDesc(Long fromUserId, Long toUserId);
}
