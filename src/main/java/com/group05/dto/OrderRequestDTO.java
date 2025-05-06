package com.group05.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO de entrada para registrar o actualizar un pedido")
public class OrderRequestDTO {

    @Schema(description = "ID del pedido (solo se usa en actualización)", example = "1")
    private Long id;

    @Schema(description = "ID del usuario que realiza el pedido", example = "5", required = true)
    private Long userId;

    @Schema(description = "Lista de productos con sus cantidades", required = true)
    private List<OrderItemRequestDTO> items;
}
