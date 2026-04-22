package com.taller.bookstore.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
public class BookRequest {

    @NotBlank(message = "must not be blank")
    @Size(max = 200, message = "must be at most 200 characters")
    private String title;

    @NotBlank(message = "must not be blank")
    @Pattern(regexp = "^(?:97[89])?[0-9]{9}[0-9Xx]$", message = "must be a valid ISBN")
    private String isbn;

    @NotNull(message = "must not be null")
    @DecimalMin(value = "0.01", inclusive = true, message = "must be greater than 0")
    private BigDecimal price;

    @NotNull(message = "must not be null")
    @Min(value = 0, message = "must be greater than or equal to 0")
    private Integer stock;

    @Size(max = 3000, message = "must be at most 3000 characters")
    private String description;

    @NotNull(message = "must not be null")
    private Long authorId;

    @NotEmpty(message = "must not be empty")
    private Set<Long> categoryIds;
}
