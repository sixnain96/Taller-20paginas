package com.taller.bookstore.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorRequest {

    @NotBlank(message = "must not be blank")
    @Size(max = 120, message = "must be at most 120 characters")
    private String name;

    @Size(max = 2000, message = "must be at most 2000 characters")
    private String biography;

    @Email(message = "must be a well-formed email address")
    private String email;

    @Size(max = 40, message = "must be at most 40 characters")
    private String phone;
}
