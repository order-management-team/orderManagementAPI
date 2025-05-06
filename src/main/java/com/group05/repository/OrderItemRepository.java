package com.group05.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.group05.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{
    List<OrderItem> findByOrderIdAndFlgState(Long orderId, String flgState);
}
