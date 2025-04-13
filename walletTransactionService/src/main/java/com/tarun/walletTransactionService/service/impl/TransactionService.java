package com.tarun.walletTransactionService.service.impl;

import com.tarun.walletTransactionService.dto.TransferInitiatedEvent;
import com.tarun.walletTransactionService.entity.WalletTransferTransaction;
import com.tarun.walletTransactionService.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void initiateTransfer(Long fromUserId, Long toUserId, Long amount) {
        WalletTransferTransaction txn = new WalletTransferTransaction();
        txn.setFromUserId(fromUserId);
        txn.setToUserId(toUserId);
        txn.setAmount(amount);
        txn.setStatus("PENDING");
        txn.setCreatedAt(LocalDateTime.now());
        txn.setUpdatedAt(LocalDateTime.now());

        txn = transactionRepository.save(txn);

        TransferInitiatedEvent event = new TransferInitiatedEvent(
                txn.getId(), fromUserId, toUserId, amount
        );

        kafkaTemplate.send("wallet.transaction.created", event);
    }
}
