package com.group05.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Respuesta para el listado de pedidos")
public class OrderListResponseDTO {
   @Schema(description = "ID único del pedido", example = "1")
    private Long id;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Schema(description = "Fecha de creación del pedido", example = "14/04/2025 18:30:00")
    private LocalDateTime date;

    @Schema(description = "Cantidad total")
    private Double totalAmount;

    @Schema(description = "Estado actual del pedido")
    private OrderStateResponseDTO state;
}
