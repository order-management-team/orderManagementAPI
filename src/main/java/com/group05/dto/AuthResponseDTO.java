package com.group05.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseDTO {
    private String username;
    private String message;
    private String jwt;
    private Boolean status;
}
