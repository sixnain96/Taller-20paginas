package com.taller.bookstore.mapper;

import com.taller.bookstore.dto.request.RegisterRequest;
import com.taller.bookstore.dto.response.UserResponse;
import com.taller.bookstore.entity.Role;
import com.taller.bookstore.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(RegisterRequest request) {
        return User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .role(Role.ROLE_USER)
                .build();
    }

    public UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
