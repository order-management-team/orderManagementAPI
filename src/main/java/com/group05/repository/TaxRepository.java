package com.group05.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.group05.model.Tax;

public interface TaxRepository extends JpaRepository<Tax, Long> {
}
