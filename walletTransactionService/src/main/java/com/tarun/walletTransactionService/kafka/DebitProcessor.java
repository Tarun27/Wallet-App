package com.tarun.walletTransactionService.kafka;

import com.tarun.walletTransactionService.dto.TransferInitiatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DebitProcessor {

    private final WalletRepository walletRepo;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(topics = "wallet.transaction.created", groupId = "wallet-transfer")
    public void handleDebit(TransferInitiatedEvent event) {
        Wallet fromWallet = walletRepo.findByUserId(event.getFromUserId())
                .orElseThrow();

        if (fromWallet.getBalance() >= event.getAmount()) {
            fromWallet.setBalance(fromWallet.getBalance() - event.getAmount());
            walletRepo.save(fromWallet);

            kafkaTemplate.send("wallet.transaction.debited", new TransferDebitedEvent(
                    event.getTransactionId(), event.getToUserId(), event.getAmount()
            ));
        } else {
            kafkaTemplate.send("wallet.transaction.completed", new TransferCompletedEvent(
                    event.getTransactionId(), "FAILED"
            ));
        }
    }
}
