package com.taller.bookstore.repository;

import com.taller.bookstore.entity.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Override
    @EntityGraph(attributePaths = {"user", "items", "items.book"})
    List<Order> findAll();

    @EntityGraph(attributePaths = {"user", "items", "items.book"})
    List<Order> findByUserId(Long userId);

    @EntityGraph(attributePaths = {"user", "items", "items.book"})
    @Query("select o from Order o where o.id = :id")
    Optional<Order> findWithDetailsById(Long id);
}
