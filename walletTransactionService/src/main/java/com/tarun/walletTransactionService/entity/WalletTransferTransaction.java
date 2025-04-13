package com.tarun.walletTransactionService.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Long amount; // in paise

    private String status; // PENDING, SUCCESS, FAILED

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
