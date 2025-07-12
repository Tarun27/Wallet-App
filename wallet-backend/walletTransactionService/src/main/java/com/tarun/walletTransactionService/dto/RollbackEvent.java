package com.tarun.walletTransactionService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RollbackEvent {
    private Long transactionId;
    private Long userId;
    private BigDecimal amount;

    // Getters and setters
}
