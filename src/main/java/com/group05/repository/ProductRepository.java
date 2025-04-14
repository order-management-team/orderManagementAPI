package com.group05.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.group05.model.Product;

public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findAllByOrderById();
}
