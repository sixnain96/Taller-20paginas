package com.taller.bookstore.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequest {

    @Valid
    @NotEmpty(message = "must not be empty")
    private List<OrderItemRequest> items;
}
