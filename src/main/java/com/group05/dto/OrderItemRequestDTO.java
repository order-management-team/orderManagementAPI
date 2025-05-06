package com.group05.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Detalle del ítem del pedido para actualización")
public class OrderItemRequestDTO {

    @Schema(description = "ID del ítem (si ya existe)", example = "1")
    private Long id;

    @Schema(description = "ID del producto", example = "10", required = true)
    private Long productId;

    @Schema(description = "Cantidad del producto solicitada", example = "3", required = true)
    private int quantity;
}
