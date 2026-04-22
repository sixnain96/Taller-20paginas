package com.taller.bookstore.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @Email(message = "must be a well-formed email address")
    @NotBlank(message = "must not be blank")
    private String email;

    @NotBlank(message = "must not be blank")
    private String password;
}
