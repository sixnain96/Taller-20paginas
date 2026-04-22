package com.taller.bookstore.service.impl;

import com.taller.bookstore.dto.request.OrderItemRequest;
import com.taller.bookstore.dto.request.OrderRequest;
import com.taller.bookstore.dto.response.OrderResponse;
import com.taller.bookstore.entity.*;
import com.taller.bookstore.exception.custom.InsufficientStockException;
import com.taller.bookstore.exception.custom.InvalidOrderStateException;
import com.taller.bookstore.exception.custom.ResourceNotFoundException;
import com.taller.bookstore.exception.custom.UnauthorizedAccessException;
import com.taller.bookstore.mapper.OrderMapper;
import com.taller.bookstore.repository.BookRepository;
import com.taller.bookstore.repository.OrderRepository;
import com.taller.bookstore.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderRepository orderRepository, BookRepository bookRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.bookRepository = bookRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    @Transactional
    public OrderResponse create(OrderRequest request, User user) {
        Order order = Order.builder()
                .user(user)
                .status(OrderStatus.PENDING)
                .createdAt(OffsetDateTime.now())
                .build();

        List<OrderItem> items = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (OrderItemRequest itemRequest : request.getItems()) {
            Book book = bookRepository.findWithDetailsById(itemRequest.getBookId())
                    .orElseThrow(() -> new ResourceNotFoundException("Book with id " + itemRequest.getBookId() + " not found"));

            if (book.getStock() < itemRequest.getQuantity()) {
                throw new InsufficientStockException("Insufficient stock for book with id " + book.getId());
            }

            BigDecimal subtotal = book.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity()));
            OrderItem item = OrderItem.builder()
                    .order(order)
                    .book(book)
                    .quantity(itemRequest.getQuantity())
                    .subtotal(subtotal)
                    .build();
            items.add(item);
            total = total.add(subtotal);
        }

        order.setItems(items);
        order.setTotal(total);
        Order savedOrder = orderRepository.save(order);
        return orderMapper.toResponse(savedOrder);
    }

    @Override
    public List<OrderResponse> getMyOrders(User user) {
        return orderRepository.findByUserId(user.getId()).stream().map(orderMapper::toResponse).toList();
    }

    @Override
    public List<OrderResponse> getAll() {
        return orderRepository.findAll().stream().map(orderMapper::toResponse).toList();
    }

    @Override
    @Transactional
    public OrderResponse confirm(Long orderId) {
        Order order = findEntity(orderId);
        if (order.getStatus() != OrderStatus.PENDING) {
            throw new InvalidOrderStateException("Order with id " + orderId + " cannot be confirmed from status " + order.getStatus());
        }

        for (OrderItem item : order.getItems()) {
            Book book = item.getBook();
            if (book.getStock() < item.getQuantity()) {
                throw new InsufficientStockException("Insufficient stock for book with id " + book.getId());
            }
            book.setStock(book.getStock() - item.getQuantity());
        }

        order.setStatus(OrderStatus.CONFIRMED);
        return orderMapper.toResponse(orderRepository.save(order));
    }

    @Override
    @Transactional
    public OrderResponse cancel(Long orderId, User user) {
        Order order = findEntity(orderId);
        if (!order.getUser().getId().equals(user.getId()) && user.getRole() != Role.ROLE_ADMIN) {
            throw new UnauthorizedAccessException("User is not allowed to cancel order with id " + orderId);
        }
        if (order.getStatus() == OrderStatus.CONFIRMED) {
            throw new InvalidOrderStateException("Order with id " + orderId + " cannot be cancelled because it is already CONFIRMED");
        }
        order.setStatus(OrderStatus.CANCELLED);
        return orderMapper.toResponse(orderRepository.save(order));
    }

    private Order findEntity(Long orderId) {
        return orderRepository.findWithDetailsById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order with id " + orderId + " not found"));
    }
}
