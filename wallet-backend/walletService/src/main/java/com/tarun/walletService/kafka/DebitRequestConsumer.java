package com.tarun.walletService.kafka;

import com.tarun.walletService.dto.DebitRequestEvent;
import com.tarun.walletService.dto.RollbackEvent;
import com.tarun.walletService.dto.TransactionStatusEvent;
import com.tarun.walletService.entity.Wallet;
import com.tarun.walletService.repository.WalletRepository;
import com.tarun.walletService.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DebitRequestConsumer {

    private final WalletRepository walletRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final WalletService walletService;

    @KafkaListener(
            topics = "wallet.transaction.debit.request",
            groupId = "wallet-group",
            containerFactory = "debitRequestKafkaListenerContainerFactory"
    )
    public void consumeDebitRequest(DebitRequestEvent event) {

        Optional<Wallet> optionalWallet = walletRepository.findByUserId(event.getFromUserId());

        if (optionalWallet.isEmpty()) {
            kafkaTemplate.send("wallet.transaction.debit.failed",
                    new TransactionStatusEvent(event.getTransactionId(), "FAILED", "DEBIT"));
            return;
        }

        Wallet wallet = optionalWallet.get();

        if (wallet.getBalance().compareTo(event.getAmount()) < 0) {
            kafkaTemplate.send("wallet.transaction.debit.failed",
                    new TransactionStatusEvent(event.getTransactionId(), "FAILED", "DEBIT"));
            return;
        }

        // ✅ Sufficient balance, debit and save
        wallet.setBalance(wallet.getBalance().subtract(event.getAmount()));
        walletRepository.save(wallet);

        kafkaTemplate.send("wallet.transaction.debit.success",
                new TransactionStatusEvent(event.getTransactionId(), "SUCCESS", "DEBIT"));
    }

    @KafkaListener(
            topics = "wallet.transaction.debit.rollback",
            groupId = "wallet-group",
            containerFactory = "rollbackKafkaListenerContainerFactory"
    )
    public void onRollbackDebit(RollbackEvent event) {
        try {
            walletService.refundToUser(event.getUserId(), event.getAmount());

            kafkaTemplate.send("wallet.transaction.debit.rolledback",
                    new TransactionStatusEvent(event.getTransactionId(), "REFUNDED", "ROLLBACK"));
        } catch (Exception ex) {
            kafkaTemplate.send("wallet.transaction.debit.rollback.failed",
                    new TransactionStatusEvent(event.getTransactionId(), "ROLLBACK_FAILED", "ROLLBACK"));
        }
    }
}
