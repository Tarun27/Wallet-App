package com.tarun.walletService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditRequestEvent {
    private Long transactionId;
    private Long userId;
    private BigDecimal amount;
}
