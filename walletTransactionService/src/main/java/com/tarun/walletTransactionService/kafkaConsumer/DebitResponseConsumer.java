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

    @KafkaListener(
            topics = "wallet.transaction.debit.success",
            groupId = "transaction-group",
            containerFactory = "transactionStatusKafkaListenerContainerFactory"
    )
    public void onDebitSuccess(TransactionStatusEvent event) {
        WalletTransferTransaction txn = transactionRepository.findById(event.getTransactionId())
                .orElseThrow();
        txn.setStatus("DEBIT_SUCCESS");
        txn.setUpdatedAt(LocalDateTime.now());
        transactionRepository.save(txn);

        // Proceed to credit
        kafkaProducer.sendCreditRequest(txn.getId(), txn.getToUserId(), txn.getAmount());
    }

    @KafkaListener(
            topics = "wallet.transaction.debit.failed",
            groupId = "transaction-group",
            containerFactory = "transactionStatusKafkaListenerContainerFactory"
    )
    public void onDebitFailed(TransactionStatusEvent event) {
        WalletTransferTransaction txn = transactionRepository.findById(event.getTransactionId())
                .orElseThrow();
        txn.setStatus("FAILED");
        txn.setUpdatedAt(LocalDateTime.now());
        transactionRepository.save(txn);
    }

    @KafkaListener(
            topics = "wallet.transaction.debit.rolledback",
            groupId = "transaction-group",
            containerFactory = "transactionStatusKafkaListenerContainerFactory"
    )
    public void onRollbackSuccess(TransactionStatusEvent event) {
        WalletTransferTransaction txn = transactionRepository.findById(event.getTransactionId())
                .orElseThrow();
        txn.setStatus("REFUNDED");
        txn.setUpdatedAt(LocalDateTime.now());
        transactionRepository.save(txn);
    }

    @KafkaListener(
            topics = "wallet.transaction.debit.rollback.failed",
            groupId = "transaction-group",
            containerFactory = "transactionStatusKafkaListenerContainerFactory"
    )
    public void onRollbackFailure(TransactionStatusEvent event) {
        WalletTransferTransaction txn = transactionRepository.findById(event.getTransactionId())
                .orElseThrow();
        txn.setStatus("ROLLBACK_FAILED");
        txn.setUpdatedAt(LocalDateTime.now());
        transactionRepository.save(txn);
    }
}
