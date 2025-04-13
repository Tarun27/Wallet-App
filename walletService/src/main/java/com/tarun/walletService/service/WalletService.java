package com.tarun.walletService.service;

import com.tarun.walletService.dto.UserCreatedEvent;
import com.tarun.walletService.entity.Wallet;

public interface WalletService {
    void createWalletForUser(UserCreatedEvent event);
    Wallet getWalletByUserId(Long userId);
    Wallet createWalletForExistingUser(Long userId);


}

