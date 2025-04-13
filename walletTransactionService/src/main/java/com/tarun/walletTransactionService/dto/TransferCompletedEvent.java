package com.tarun.walletTransactionService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferCompletedEvent {
    private Long transactionId;
    private String finalStatus; // SUCCESS or FAILED
}
