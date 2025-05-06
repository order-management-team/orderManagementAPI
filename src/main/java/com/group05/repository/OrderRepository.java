package com.group05.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.group05.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findByUserIdAndFlgStateAndUser_FlgState(Pageable pageable, Long userId, String orderState, String userState);
    Optional<Order> findByIdAndFlgState(Long orderId, String orderState);
}
