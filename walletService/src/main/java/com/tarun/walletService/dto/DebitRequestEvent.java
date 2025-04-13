package com.tarun.walletService.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DebitRequestEvent {
    private Long transactionId;
    private Long userId;
    private BigDecimal amount;
}
