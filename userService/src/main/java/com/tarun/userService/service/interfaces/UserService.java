package com.tarun.userService.service.interfaces;

import com.tarun.userService.dto.*;

public interface UserService {
    RegisterResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

    UserResponse getUserById(Long id);
    UserResponse getUserByEmail(String email);


}
