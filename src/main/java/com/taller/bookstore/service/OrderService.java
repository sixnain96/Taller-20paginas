package com.taller.bookstore.service;

import com.taller.bookstore.dto.request.OrderRequest;
import com.taller.bookstore.dto.response.OrderResponse;
import com.taller.bookstore.entity.User;

import java.util.List;

public interface OrderService {
    OrderResponse create(OrderRequest request, User user);
    List<OrderResponse> getMyOrders(User user);
    List<OrderResponse> getAll();
    OrderResponse confirm(Long orderId);
    OrderResponse cancel(Long orderId, User user);
}
