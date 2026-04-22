package com.taller.bookstore.dto.response;

import com.taller.bookstore.entity.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthResponse {
    private String token;
    private Long expiresIn;
    private Role role;
    private UserResponse user;
}
