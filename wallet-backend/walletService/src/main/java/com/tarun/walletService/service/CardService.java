package com.tarun.walletService.service;

import com.tarun.walletService.dto.AddCardRequest;
import com.tarun.walletService.dto.CardResponse;

import java.util.List;

public interface CardService {
    List<CardResponse> getCardsForUser(Long userId);
    CardResponse addCard(Long userId, AddCardRequest req);
    void deleteCard(Long cardId, Long userId);
}
