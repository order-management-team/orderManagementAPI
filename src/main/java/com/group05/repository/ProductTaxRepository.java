package com.group05.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.group05.model.ProductTax;

public interface ProductTaxRepository extends JpaRepository<ProductTax, Long> {
    List<ProductTax> findByProductIdAndFlgState(Long productId, String flgState);
}
