package com.tarun.userService.service.impl;

import com.tarun.userService.dto.LoginRequest;
import com.tarun.userService.dto.LoginResponse;
import com.tarun.userService.dto.RegisterRequest;
import com.tarun.userService.dto.RegisterResponse;
import com.tarun.userService.entity.User;
import com.tarun.userService.repository.UserRepository;
import com.tarun.userService.security.JwtUtil;
import com.tarun.userService.service.interfaces.UserService;
import com.tarun.userService.util.PasswordEncoderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;


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

}
