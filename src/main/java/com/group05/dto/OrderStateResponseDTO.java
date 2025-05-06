package com.group05.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Estado asociado a un pedido")
public class OrderStateResponseDTO {

    @Schema(description = "ID del estado", example = "1")
    private Long id;

    @Schema(description = "Descripción o nombre del estado", example = "CREACIÓN")
    private String name;
}