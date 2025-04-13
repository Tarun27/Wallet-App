package com.tarun.walletTransactionService.controller;

import com.tarun.walletTransactionService.service.impl.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/initiate")
    public ResponseEntity<String> initiateTransfer(@RequestParam Long fromUserId,
                                                   @RequestParam Long toUserId,
                                                   @RequestParam Long amount) {
        transactionService.initiateTransfer(fromUserId, toUserId, amount);
        return ResponseEntity.ok("Transaction initiated");
    }
}
