package com.tarun.walletTransactionService.service;

import com.tarun.walletTransactionService.dto.CreditRequestEvent;
import com.tarun.walletTransactionService.dto.DebitRequestEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendDebitRequest(Long txnId, Long userId, BigDecimal amount) {
        kafkaTemplate.send("wallet.transaction.debit.request", new DebitRequestEvent(txnId, userId, amount));
    }

    public void sendCreditRequest(Long txnId, Long userId, BigDecimal amount) {
        kafkaTemplate.send("wallet.transaction.credit.request", new CreditRequestEvent(txnId, userId, amount));
    }
}
