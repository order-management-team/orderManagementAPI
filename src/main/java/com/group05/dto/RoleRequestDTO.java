package com.group05.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO utilizado para crear o actualizar roles en el sistema")
public class RoleRequestDTO {

    @Schema(description = "ID del rol (usado para actualizaciones)", example = "1")
    private Long id;

    @Schema(description = "Nombre del rol (único)", example = "ADMIN", required = true)
    private String name;

    @Schema(description = "Descripción del rol", example = "Rol con acceso completo al sistema")
    private String description;
}
