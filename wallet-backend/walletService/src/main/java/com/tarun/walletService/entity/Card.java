package com.tarun.walletService.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cards")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;     // "Visa", "MasterCard", etc.
    private String last4;    // Last 4 digits
    private String exp;      // "MM/YY"
    private String nameOnCard;

    @Column(nullable = false)
    private Long userId;     // Foreign key to User or Wallet, as per your design

    // Add more fields as needed (card tokenization for real world)
}
