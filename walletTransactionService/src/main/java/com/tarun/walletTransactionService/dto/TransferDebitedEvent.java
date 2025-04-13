package com.tarun.walletTransactionService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferDebitedEvent {
    private Long transactionId;
    private Long toUserId;
    private Long amount;
}
