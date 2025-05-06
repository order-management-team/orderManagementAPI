package com.group05.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(name = "ProductTaxResponse", description = "DTO que representa un impuesto aplicado.")
public class ProductTaxResponseDTO {

    @Schema(description = "ID del impuesto asociado al producto.", example = "1")
    private Long id;

    private Long taxId;

    @Schema(description = "Nombre del impuesto.", example = "IGV")
    private String name;

    @Schema(description = "Tasa del impuesto.", example = "0.18")
    private BigDecimal rate;
}
