package com.group05.dto;

import java.math.BigDecimal;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para recibir datos de creación o edición de productos")
public class ProductRequestDTO {

    @NotBlank(message = "El nombre no puede estar vacío")
    @Schema(description = "Nombre del producto", example = "Teclado HyperX Alloy Origins")
    private String name;

    @Min(value = 0, message = "El stock no puede ser negativo")
    @Schema(description = "Cantidad en stock disponible", example = "50")
    private Long stock;

    @DecimalMin(value = "0.01", inclusive = true, message = "El precio debe ser mayor a cero")
    @Schema(description = "Precio del producto", example = "299.99")
    private BigDecimal price;

    @Schema(description = "Descripción del producto", example = "Teclado mecánico con retroiluminación RGB")
    private String description;

    @Schema(description = "Lista de impuestos aplicados al producto.")
    private List<Long> taxesId;
}
