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
public class CreditResponseConsumer {

    private final TransactionRepository transactionRepository;
    private final KafkaProducerService kafkaProducer;

    @KafkaListener(topics = "wallet.transaction.credit.success", groupId = "transaction-group")
    public void onCreditSuccess(TransactionStatusEvent event) {
        WalletTransferTransaction txn = transactionRepository.findById(event.getTransactionId())
                .orElseThrow();
        txn.setStatus("SUCCESS");
        txn.setUpdatedAt(LocalDateTime.now());
        transactionRepository.save(txn);
    }

    @KafkaListener(topics = "wallet.transaction.credit.failed", groupId = "transaction-group")
    public void onCreditFailed(TransactionStatusEvent event) {
        WalletTransferTransaction txn = transactionRepository.findById(event.getTransactionId())
                .orElseThrow();
        txn.setStatus("FAILED");
        txn.setUpdatedAt(LocalDateTime.now());
        transactionRepository.save(txn);

        // Emit rollback request
        kafkaProducer.sendRollbackDebitRequest(txn.getId(), txn.getFromUserId(), txn.getAmount());
    }
}
