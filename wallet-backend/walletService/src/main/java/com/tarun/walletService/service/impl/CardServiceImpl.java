package com.tarun.walletService.service.impl;

import com.tarun.walletService.dto.AddCardRequest;
import com.tarun.walletService.dto.CardResponse;
import com.tarun.walletService.entity.Card;
import com.tarun.walletService.repository.CardRepository;
import com.tarun.walletService.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;

    @Override
    public List<CardResponse> getCardsForUser(Long userId) {
        return cardRepository.findByUserId(userId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CardResponse addCard(Long userId, AddCardRequest req) {
        Card card = new Card();
        card.setUserId(userId);
        card.setType(req.getType());
        card.setLast4(req.getLast4());
        card.setExp(req.getExp());
        card.setNameOnCard(req.getNameOnCard());
        Card saved = cardRepository.save(card);
        return toResponse(saved);
    }

    @Override
    public void deleteCard(Long cardId, Long userId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));
        if (!card.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized");
        }
        cardRepository.delete(card);
    }

    private CardResponse toResponse(Card card) {
        CardResponse resp = new CardResponse();
        resp.setId(card.getId());
        resp.setType(card.getType());
        resp.setLast4(card.getLast4());
        resp.setExp(card.getExp());
        resp.setNameOnCard(card.getNameOnCard());
        return resp;
    }
}
