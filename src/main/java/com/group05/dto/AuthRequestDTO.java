package com.group05.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Schema(description = "DTO utilizado para solicitar autenticación de usuario")
public class AuthRequestDTO {

    @NotEmpty(message = "El email no puede estar vacío")
    @Schema(description = "Correo electrónico del usuario", example = "juan@gmail.com", required = true)
    private String email;

    @NotEmpty(message = "La contraseña no puede estar vacía")
    @Schema(description = "Contraseña del usuario", example = "ClaveSegura123!", required = true)
    private String password;
}
