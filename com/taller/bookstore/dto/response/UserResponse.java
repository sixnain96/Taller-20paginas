package com.taller.bookstore.dto.response;

import com.taller.bookstore.entity.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {
    private Long id;
    private String fullName;
    private String email;
    private Role role;
}
