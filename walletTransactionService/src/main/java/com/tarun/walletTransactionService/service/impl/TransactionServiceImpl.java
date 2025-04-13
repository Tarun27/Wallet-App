package com.tarun.walletTransactionService.service.impl;

import com.tarun.walletTransactionService.dto.TransferInitiatedEvent;
import com.tarun.walletTransactionService.entity.WalletTransferTransaction;
import com.tarun.walletTransactionService.repository.TransactionRepository;
import com.tarun.walletTransactionService.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

        private final TransactionRepository transactionRepository;
        private final KafkaTemplate<String, Object> kafkaTemplate;

        @Override
        public void initiateTransfer(Long fromUserId, Long toUserId, BigDecimal amount) {
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


    @Override
    public WalletTransferTransaction getTransactionById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found with ID: " + id));
    }

}

