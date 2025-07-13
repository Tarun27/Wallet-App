package com.tarun.walletService.controller;

import com.tarun.walletService.dto.AddCardRequest;
import com.tarun.walletService.dto.CardResponse;
import com.tarun.walletService.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wallets")
@RequiredArgsConstructor
public class CardsController {

    private final CardService cardService;

    // List cards for user (optionally get userId from JWT/auth)
    @GetMapping("/{userId}/cards")
    public ResponseEntity<List<CardResponse>> getCards(@PathVariable Long userId) {
        return ResponseEntity.ok(cardService.getCardsForUser(userId));
    }

    @PostMapping("/{userId}/cards")
    public ResponseEntity<CardResponse> addCard(
            @PathVariable Long userId,
            @RequestBody AddCardRequest req
    ) {
        return ResponseEntity.ok(cardService.addCard(userId, req));
    }

    @DeleteMapping("/cards/{cardId}")
    public ResponseEntity<Void> deleteCard(
            @PathVariable Long cardId,
            @RequestParam Long userId // or get from auth
    ) {
        cardService.deleteCard(cardId, userId);
        return ResponseEntity.noContent().build();
    }
}

