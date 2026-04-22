package com.taller.bookstore.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class OrderItemResponse {
    private Long id;
    private Long bookId;
    private String bookTitle;
    private Integer quantity;
    private BigDecimal subtotal;
}
