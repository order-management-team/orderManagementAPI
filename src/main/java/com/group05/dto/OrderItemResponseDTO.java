package com.group05.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para representar un producto incluido en un pedido")
public class OrderItemResponseDTO {

    @Schema(description = "ID único del ítem del pedido", example = "1")
    private Long id;

    @Schema(description = "ID del producto solicitado", example = "10")
    private Long productId;

    @Schema(description = "Nombre del producto solicitado", example = "Laptop Lenovo")
    private String productName;

    @Schema(description = "Cantidad del producto en el pedido", example = "2")
    private int quantity;

    @Schema(description = "Precio unitario del producto")
    private BigDecimal unitPrice;

    @Schema(description = "Precio total por item")
    private BigDecimal subtotal;
}
