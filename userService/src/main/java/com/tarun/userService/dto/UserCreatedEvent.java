package com.tarun.userService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreatedEvent {
    private Long userId;
    private String email;
    private String name;
    private LocalDateTime createdAt;
}
