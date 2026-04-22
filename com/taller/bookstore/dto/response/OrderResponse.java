package com.taller.bookstore.dto.response;

import com.taller.bookstore.entity.OrderStatus;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Builder
public class OrderResponse {
    private Long id;
    private Long userId;
    private String userEmail;
    private OrderStatus status;
    private BigDecimal total;
    private OffsetDateTime createdAt;
    private List<OrderItemResponse> items;
}
