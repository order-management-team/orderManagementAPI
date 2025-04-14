package com.group05.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO utilizado para devolver información de un producto registrado en el sistema.")
public class ProductResponseDTO {

    @Schema(description = "ID único del producto", example = "1")
    private Long id;

    @Schema(description = "Nombre del producto", example = "Teclado HyperX Alloy Origins")
    private String name;

    @Schema(description = "Cantidad en stock disponible", example = "50")
    private Long stock;

    @Schema(description = "Precio del producto", example = "299.99")
    private Double price;

    @Schema(description = "Descripción del producto", example = "Teclado mecánico con retroiluminación RGB")
    private String description;
}
