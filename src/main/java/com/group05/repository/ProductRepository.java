package com.group05.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.group05.model.Product;

public interface ProductRepository extends JpaRepository<Product,Long> {
    Page<Product> findByFlgStateOrderById(Pageable pageable, String flgState);

}
