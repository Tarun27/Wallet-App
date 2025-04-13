package com.tarun.walletService.service.impl;

import com.tarun.walletService.dto.UserCreatedEvent;
import com.tarun.walletService.entity.Wallet;
import com.tarun.walletService.repository.WalletRepository;
import com.tarun.walletService.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    @Override
    public void createWalletForUser(UserCreatedEvent event) {
        Wallet wallet = new Wallet();
        wallet.setUserId(event.getUserId());
        wallet.setBalance(BigDecimal.ZERO);
        wallet.setCurrency("INR");
        wallet.setCreatedAt(LocalDateTime.now());

        walletRepository.save(wallet);
    }

    @Override
    public Wallet getWalletByUserId(Long userId) {
        return walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));
    }

    @Override
    public Wallet createWalletForExistingUser(Long userId) {
        if (walletRepository.findByUserId(userId).isPresent()) {
            throw new RuntimeException("Wallet already exists for userId: " + userId);
        }

        Wallet wallet = new Wallet();
        wallet.setUserId(userId);
        wallet.setBalance(BigDecimal.ZERO);
        wallet.setCurrency("INR");
        wallet.setCreatedAt(LocalDateTime.now());

        return walletRepository.save(wallet);
    }

}
