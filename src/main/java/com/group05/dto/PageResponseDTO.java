package com.group05.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(name = "PageResponseDto", description = "Respuesta de datos del paginado")
public class PageResponseDTO {
    @Schema(description = "Cantidad del paginado")
    private Long size;

    @Schema(description = "Total de elementos existentes")
    private Long totalElements;

    @Schema(description = "Total de paginas")
    private Long totalPages;

    @Schema(description = "Numero de la pagina")
    private Long number;
}
