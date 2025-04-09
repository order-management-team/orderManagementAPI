package com.group05.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO para representar los datos de usuario en las respuestas")
public class UserResponseDTO {

    @Schema(description = "ID único del usuario", example = "1")
    private Long id;

    @Schema(description = "Nombre del usuario", example = "Juan")
    private String name;

    @Schema(description = "Correo electrónico del usuario", example = "juan@gmail.com")
    private String email;

    @Schema(description = "Fecha de registro del usuario", example = "08/04/2025")
    private LocalDate signUpDate;

    @Schema(description = "Monto total gastado por el usuario", example = "150.75")
    private Double totalSpent;

    @Schema(description = "Rol asignado al usuario")
    private RoleResponseDTO role;
}
