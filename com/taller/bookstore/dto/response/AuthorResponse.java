package com.taller.bookstore.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthorResponse {
    private Long id;
    private String name;
    private String biography;
    private String email;
    private String phone;
}
