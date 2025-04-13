package com.tarun.walletService.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DebitRequest {
    private Long userId;
    private BigDecimal amount;
    private String reference; // optional: for idempotency/debugging
}