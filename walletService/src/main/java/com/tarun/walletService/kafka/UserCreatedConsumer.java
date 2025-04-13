package com.tarun.walletService.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tarun.walletService.dto.UserCreatedEvent;
import com.tarun.walletService.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserCreatedConsumer {

    private final WalletService walletService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "user.created", groupId = "wallet-service")
    public void consume(String message) throws JsonProcessingException {
        UserCreatedEvent event = objectMapper.readValue(message, UserCreatedEvent.class);
        walletService.createWalletForUser(event);
    }

}
