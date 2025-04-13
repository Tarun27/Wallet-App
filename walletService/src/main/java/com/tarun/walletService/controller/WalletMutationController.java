package com.tarun.walletService.controller;

import com.tarun.walletService.dto.CreditRequest;
import com.tarun.walletService.dto.DebitRequest;
import com.tarun.walletService.dto.WalletMutationResponse;
import com.tarun.walletService.service.WalletMutationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wallets")
@RequiredArgsConstructor
public class WalletMutationController {

    private final WalletMutationService walletMutationService;

    @PostMapping("/debit")
    public ResponseEntity<WalletMutationResponse> debit(@RequestBody DebitRequest request) {
        WalletMutationResponse response = walletMutationService.debit(request);
        return response.isSuccess()
                ? ResponseEntity.ok(response)
                : ResponseEntity.badRequest().body(response);
    }

    @PostMapping("/credit")
    public ResponseEntity<WalletMutationResponse> credit(@RequestBody CreditRequest request) {
        WalletMutationResponse response = walletMutationService.credit(request);
        return ResponseEntity.ok(response);
    }
}
