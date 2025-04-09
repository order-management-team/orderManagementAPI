package com.group05.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "DTO de respuesta al autenticarse correctamente")
public class AuthResponseDTO {

    @Schema(description = "Email del usuario autenticado", example = "juan@gmail.com")
    private String username;

    @Schema(description = "Mensaje descriptivo del resultado del login", example = "Usuario autenticado correctamente")
    private String message;

    @Schema(description = "Token JWT generado para el usuario", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String jwt;

    @Schema(description = "Estado del proceso de autenticación", example = "true")
    private Boolean status;
}
