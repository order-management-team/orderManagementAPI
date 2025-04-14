package com.group05.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "DTO para recibir datos de creación o edición de usuarios")
public class UserRequestDTO {

    @Schema(description = "ID del usuario (opcional en creación)", example = "1")
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Schema(description = "Nombre del usuario", example = "Juan", required = true)
    private String name;

    @NotBlank(message = "El correo no puede estar vacío")
    @Schema(description = "Correo electrónico del usuario", example = "juan@gmail.com", required = true)
    private String email;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*()]).{8,}$", message = "La contraseña debe tener al menos 8 caracteres, un número y un símbolo especial")
    @Schema(description = "Contraseña del usuario", example = "ClaveSegura123!", required = true)
    private String password;

    @NotNull(message = "El rol es obligatorio")
    @Schema(description = "ID del rol asignado al usuario", example = "2", required = true)
    private Long roleId;
}
