package com.tarun.walletService.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tarun.walletService.dto.AddCardRequest;
import com.tarun.walletService.entity.Card;
import com.tarun.walletService.repository.CardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CardsControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Long testUserId = 1L;

    @BeforeEach
    void setUp() {
        cardRepository.deleteAll();
    }

    @Test
    void testAddCardAndGetCards() throws Exception {
        AddCardRequest req = new AddCardRequest();
        req.setType("Visa");
        req.setLast4("1234");
        req.setExp("12/25");
        req.setNameOnCard("Test User");

        // Add card
        ResultActions addResult = mockMvc.perform(post("/wallets/" + testUserId + "/cards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)));
        addResult.andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value("Visa"))
                .andExpect(jsonPath("$.last4").value("1234"));

        // Get cards
        ResultActions getResult = mockMvc.perform(get("/wallets/" + testUserId + "/cards"));
        getResult.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type").value("Visa"))
                .andExpect(jsonPath("$[0].last4").value("1234"));
    }

    @Test
    void testDeleteCard() throws Exception {
        // Add a card first
        Card card = new Card();
        card.setType("MasterCard");
        card.setLast4("5678");
        card.setExp("11/24");
        card.setNameOnCard("Delete User");
        card.setUserId(testUserId);
        card = cardRepository.save(card);

        // Delete card
        ResultActions deleteResult = mockMvc.perform(delete("/wallets/cards/" + card.getId())
                .param("userId", testUserId.toString()));
        deleteResult.andExpect(status().isNoContent());

        // Verify card is deleted
        Optional<Card> deleted = cardRepository.findById(card.getId());
        assertThat(deleted).isEmpty();
    }

    @Test
    void testGetCardsEmpty() throws Exception {
        mockMvc.perform(get("/wallets/" + testUserId + "/cards"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void testDeleteCardNotFound() throws Exception {
        mockMvc.perform(delete("/wallets/cards/99999")
                .param("userId", testUserId.toString()))
                .andExpect(status().isNoContent()); // Should still return 204
    }
}

