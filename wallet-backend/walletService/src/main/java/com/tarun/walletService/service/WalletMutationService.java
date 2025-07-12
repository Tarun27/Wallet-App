package com.tarun.walletService.service;

import com.tarun.walletService.dto.CreditRequest;
import com.tarun.walletService.dto.DebitRequest;
import com.tarun.walletService.dto.WalletMutationResponse;
import com.tarun.walletService.entity.Wallet;
import com.tarun.walletService.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletMutationService {

    private final WalletRepository walletRepository;

    public WalletMutationResponse debit(DebitRequest request) {
        Wallet wallet = walletRepository.findByUserId(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        if (wallet.getBalance().compareTo(request.getAmount()) < 0) {
            return new WalletMutationResponse(false, "Insufficient balance");
        }

        wallet.setBalance(wallet.getBalance().subtract(request.getAmount()));
        walletRepository.save(wallet);

        return new WalletMutationResponse(true, "Debited successfully");
    }

    public WalletMutationResponse credit(CreditRequest request) {
        Wallet wallet = walletRepository.findByUserId(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        wallet.setBalance(wallet.getBalance().add(request.getAmount()));
        walletRepository.save(wallet);

        return new WalletMutationResponse(true, "Credited successfully");
    }
}
