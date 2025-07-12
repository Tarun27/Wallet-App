package com.tarun.walletService.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DebitRequestEvent {
    private Long transactionId;
    private Long fromUserId;
    private Long toUserId;
    private BigDecimal amount;

    // Getters and Setters
}
