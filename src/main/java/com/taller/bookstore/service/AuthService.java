package com.taller.bookstore.service;

import com.taller.bookstore.dto.request.LoginRequest;
import com.taller.bookstore.dto.request.RegisterRequest;
import com.taller.bookstore.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}
