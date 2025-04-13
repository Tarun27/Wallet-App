package com.tarun.userService.service.impl;

import com.tarun.userService.dto.*;
import com.tarun.userService.entity.User;
import com.tarun.userService.repository.UserRepository;
import com.tarun.userService.security.JwtUtil;
import com.tarun.userService.service.interfaces.UserService;
import com.tarun.userService.util.PasswordEncoderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final KafkaTemplate<String, Object> kafkaTemplate;


    @Override
    public RegisterResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(PasswordEncoderUtil.encode(request.getPassword()));

        user = userRepository.save(user);

        UserCreatedEvent event = new UserCreatedEvent(
                user.getId(),
                user.getEmail(),
                user.getName(),
                LocalDateTime.now()
        );

        kafkaTemplate.send("user.created", event);


        return new RegisterResponse(user.getId(), user.getEmail(), "User registered successfully");
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        boolean valid = PasswordEncoderUtil.matches(request.getPassword(), user.getPassword());

        if (!valid) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(user.getEmail());
        return new LoginResponse(token, "Login successful");
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }


}
