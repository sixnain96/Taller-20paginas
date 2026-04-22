package com.taller.bookstore.controller;

import com.taller.bookstore.config.ApiResponseBuilder;
import com.taller.bookstore.dto.request.OrderRequest;
import com.taller.bookstore.dto.response.ApiResponse;
import com.taller.bookstore.dto.response.OrderResponse;
import com.taller.bookstore.entity.User;
import com.taller.bookstore.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final ApiResponseBuilder responseBuilder;

    public OrderController(OrderService orderService, ApiResponseBuilder responseBuilder) {
        this.orderService = orderService;
        this.responseBuilder = responseBuilder;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponse>> create(@Valid @RequestBody OrderRequest request,
                                                             @AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(responseBuilder.success(HttpStatus.CREATED, "Pedido creado correctamente", orderService.create(request, user)));
    }

    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getMyOrders(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(responseBuilder.success(HttpStatus.OK, "Pedidos del usuario obtenidos correctamente", orderService.getMyOrders(user)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getAll() {
        return ResponseEntity.ok(responseBuilder.success(HttpStatus.OK, "Pedidos obtenidos correctamente", orderService.getAll()));
    }

    @PatchMapping("/{id}/confirm")
    public ResponseEntity<ApiResponse<OrderResponse>> confirm(@PathVariable Long id) {
        return ResponseEntity.ok(responseBuilder.success(HttpStatus.OK, "Pedido confirmado correctamente", orderService.confirm(id)));
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse<OrderResponse>> cancel(@PathVariable Long id, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(responseBuilder.success(HttpStatus.OK, "Pedido cancelado correctamente", orderService.cancel(id, user)));
    }
}
