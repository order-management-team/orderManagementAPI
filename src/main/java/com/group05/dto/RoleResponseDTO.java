package com.group05.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO de respuesta que representa los datos de un rol")
public class RoleResponseDTO {

    @Schema(description = "ID único del rol", example = "1")
    private Long id;

    @Schema(description = "Nombre del rol", example = "CLIENT")
    private String name;

    @Schema(description = "Descripción del rol", example = "Rol con permisos para crear y ver pedidos")
    private String description;
}
