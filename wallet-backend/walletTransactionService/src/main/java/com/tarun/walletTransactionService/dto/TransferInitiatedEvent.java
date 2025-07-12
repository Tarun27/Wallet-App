package com.tarun.walletTransactionService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferInitiatedEvent {
    private Long transactionId;
    private Long fromUserId;
    private Long toUserId;
    private BigDecimal amount;
}
