package com.tarun.walletService.kafka;


import com.tarun.walletService.dto.DebitRequestEvent;
import com.tarun.walletService.dto.TransactionStatusEvent;
import com.tarun.walletService.entity.Wallet;
import com.tarun.walletService.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DebitRequestConsumer {

    private final WalletRepository walletRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(topics = "wallet.transaction.debit.request", groupId = "wallet-group")
    public void consumeDebitRequest(DebitRequestEvent event) {
        Optional<Wallet> optionalWallet = walletRepository.findByUserId(event.getUserId());

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

        // Sufficient balance, debit and save
        wallet.setBalance(wallet.getBalance().subtract(event.getAmount()));
        walletRepository.save(wallet);

        kafkaTemplate.send("wallet.transaction.debit.success",
                new TransactionStatusEvent(event.getTransactionId(), "SUCCESS", "DEBIT"));
    }
}
