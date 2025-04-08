package com.group05.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.group05.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByEmail(String email);
}
