package com.group05.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.group05.model.OrderState;

public interface OrderStateRepository extends JpaRepository<OrderState, Long> {
}
