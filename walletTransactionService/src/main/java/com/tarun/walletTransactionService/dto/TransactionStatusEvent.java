package com.tarun.walletTransactionService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionStatusEvent {
    private Long transactionId;
    private String status; // SUCCESS or FAILED
    private String stage;  // DEBIT or CREDIT
}
