package com.group05.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(name = "PaginationResponseDto", description = "Respuesta del listado de objetos y el paginado")
public class PaginationResponseDTO<T> {
    @Schema(description = "Objeto del paginado")
    private T content;

    @Schema(description = "Numero de la pagina")
    private PageResponseDTO page;
}
