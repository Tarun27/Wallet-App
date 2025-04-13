package com.tarun.walletService.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreditRequest {
    private Long userId;
    private BigDecimal amount; // in paise
    private String reference; // optional
}
