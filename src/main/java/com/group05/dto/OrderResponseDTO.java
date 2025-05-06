package com.group05.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Respuesta detallada de un pedido")
public class OrderResponseDTO {

    @Schema(description = "ID único del pedido", example = "1")
    private Long id;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Schema(description = "Fecha de creación del pedido", example = "14/04/2025 18:30:00")
    private LocalDateTime date;

    @Schema(description = "Estado actual del pedido")
    private OrderStateResponseDTO state;

    @Schema(description = "ID del usuario que realizó el pedido", example = "5")
    private Long userId;

    @Schema(description = "Nombre del usuario que realizó el pedido", example = "Juan Pérez")
    private String userName;

    @Schema(description = "Correo electrónico del usuario", example = "juan.perez@example.com")
    private String userEmail;

    @Schema(description = "Cantidad total")
    private BigDecimal totalAmount;

    @Schema(description = "Lista de productos incluidos en el pedido")
    private List<OrderItemResponseDTO> items;
}
