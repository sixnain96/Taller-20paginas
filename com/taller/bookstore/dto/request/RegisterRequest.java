package com.taller.bookstore.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    @NotBlank(message = "must not be blank")
    private String fullName;

    @Email(message = "must be a well-formed email address")
    @NotBlank(message = "must not be blank")
    private String email;

    @Size(min = 8, message = "must have at least 8 characters")
    @NotBlank(message = "must not be blank")
    private String password;
}
