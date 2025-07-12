package com.tarun.walletTransactionService.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "wallet_transfer_transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletTransferTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long fromUserId;
    private Long toUserId;

    @Column(nullable = false)
    private BigDecimal amount;

    private String status; // PENDING, SUCCESS, FAILED

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
