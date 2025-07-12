package com.tarun.walletService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionStatusEvent {
    private Long transactionId;
    private String status; // "SUCCESS" or "FAILED"
    private String stage;  // "DEBIT"
}
