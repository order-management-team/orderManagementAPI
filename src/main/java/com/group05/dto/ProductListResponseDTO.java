package com.group05.dto;

import java.math.BigDecimal;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO utilizado para devolver información básica de un producto para el listado principal.")
public class ProductListResponseDTO {
    @Schema(description = "ID único del producto", example = "1")
    private Long id;

    @Schema(description = "Nombre del producto", example = "Teclado HyperX Alloy Origins")
    private String name;

    @Schema(description = "Cantidad en stock disponible", example = "50")
    private Long stock;

    @Schema(description = "Precio del producto", example = "299.99")
    private BigDecimal price;

    @Schema(description = "Descripción del producto", example = "Teclado mecánico con retroiluminación RGB")
    private String description;
}
