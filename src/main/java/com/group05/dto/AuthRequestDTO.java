package com.group05.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AuthRequestDTO {

    @NotEmpty(message = "El email no puede estar vacío")
    private String email;

    @NotEmpty(message = "La contraseña no puede estar vacía")
    private String password;
}
