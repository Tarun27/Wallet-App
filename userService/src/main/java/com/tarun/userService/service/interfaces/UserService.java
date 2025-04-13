package com.tarun.userService.service.interfaces;

import com.tarun.userService.dto.LoginRequest;
import com.tarun.userService.dto.LoginResponse;
import com.tarun.userService.dto.RegisterRequest;
import com.tarun.userService.dto.RegisterResponse;

public interface UserService {
    RegisterResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

}
