package com.group05.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO para recibir datos de creación o edición de usuarios")
public class UserRequestDTO {

    @Schema(description = "ID del usuario (opcional en creación)", example = "1")
    private Long id;

    @Schema(description = "Nombre del usuario", example = "Juan", required = true)
    private String name;

    @Schema(description = "Correo electrónico del usuario", example = "juan@gmail.com", required = true)
    private String email;

    @Schema(description = "Contraseña del usuario", example = "ClaveSegura123!", required = true)
    private String password;

    @Schema(description = "ID del rol asignado al usuario", example = "2", required = true)
    private Long roleId;
}
