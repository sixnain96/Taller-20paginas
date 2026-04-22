package com.taller.bookstore.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemRequest {

    @NotNull(message = "must not be null")
    private Long bookId;

    @NotNull(message = "must not be null")
    @Min(value = 1, message = "must be greater than 0")
    private Integer quantity;
}
