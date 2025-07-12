package com.tarun.walletTransactionService.controller;


import com.tarun.walletTransactionService.dto.TransferRequest;
import com.tarun.walletTransactionService.entity.WalletTransferTransaction;
import com.tarun.walletTransactionService.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/initiate")
    public ResponseEntity<String> initiateTransfer(@RequestBody TransferRequest request) {
        transactionService.initiateTransfer(
                request.getFromUserId(),
                request.getToUserId(),
                request.getAmount()
        );
        return ResponseEntity.ok("Transaction initiated");
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<WalletTransferTransaction> getTransactionById(@PathVariable Long transactionId) {
        WalletTransferTransaction txn = transactionService.getTransactionById(transactionId);
        return ResponseEntity.ok(txn);
    }

}
