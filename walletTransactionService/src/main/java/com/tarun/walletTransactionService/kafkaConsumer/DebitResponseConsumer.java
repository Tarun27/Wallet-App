package com.tarun.walletTransactionService.kafkaConsumer;

import com.tarun.walletTransactionService.dto.TransactionStatusEvent;
import com.tarun.walletTransactionService.entity.WalletTransferTransaction;
import com.tarun.walletTransactionService.repository.TransactionRepository;
import com.tarun.walletTransactionService.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DebitResponseConsumer {

    private final TransactionRepository transactionRepository;
    private final KafkaProducerService kafkaProducer;

    @KafkaListener(topics = "wallet.transaction.debit.success", groupId = "transaction-group")
    public void onDebitSuccess(TransactionStatusEvent event) {
        WalletTransferTransaction txn = transactionRepository.findById(event.getTransactionId()).orElseThrow();
        // Proceed to credit
        kafkaProducer.sendCreditRequest(txn.getId(), txn.getToUserId(), txn.getAmount());
    }

    @KafkaListener(topics = "wallet.transaction.debit.failed", groupId = "transaction-group")
    public void onDebitFailed(TransactionStatusEvent event) {
        WalletTransferTransaction txn = transactionRepository.findById(event.getTransactionId()).orElseThrow();
        txn.setStatus("FAILED");
        txn.setUpdatedAt(LocalDateTime.now());
        transactionRepository.save(txn);
    }
}

