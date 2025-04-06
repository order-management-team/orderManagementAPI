package com.group05.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class UserResponseDTO {
    private Long id;

    private String name;

    private String email;

    private LocalDate signUpDate;
    
    private Double totalSpent;

    private RoleResponseDTO role;
}
