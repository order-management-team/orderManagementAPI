package com.group05.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.group05.dto.OrderListResponseDTO;
import com.group05.dto.OrderRequestDTO;
import com.group05.dto.OrderResponseDTO;
import com.group05.dto.PaginationResponseDTO;
import com.group05.exceptionHandler.ErrorDetails;
import com.group05.service.use_cases.OrderUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


@RestController
@RequestMapping("/orders")
public class OrderController {

    private OrderUseCase orderUseCase;

    public OrderController(OrderUseCase orderUseCase){
        this.orderUseCase = orderUseCase;
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Listar órdenes por usuario",
        description = "Servicio encargado de listar las órdenes asociadas a un usuario de forma paginada"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Listado de órdenes del usuario",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = PaginationResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Usuario no encontrado",
            content = @Content(schema = @Schema(implementation = ErrorDetails.class))
        )
    })
    public ResponseEntity<PaginationResponseDTO<List<OrderListResponseDTO>>> getOrdersByUser(
            @PathVariable Long id,
            @Parameter(description = "Filtro de pagina") @RequestParam(value = "page", defaultValue = "0") Long pageNumber,
            @Parameter(description = "Filtro de cantidad") @RequestParam(value = "size", defaultValue = "10") Long pageSize
        ){
        return ResponseEntity.ok(orderUseCase.getOrdersByUser(id, pageNumber, pageSize));
    }

    @GetMapping("/detail/{id}")
    @Operation(
        summary = "Detalle de una orden",
        description = "Obtiene la información completa de una orden por su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Orden encontrada",
            content = @Content(schema = @Schema(implementation = OrderResponseDTO.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Orden no encontrada",
            content = @Content(schema = @Schema(implementation = ErrorDetails.class))
        )
    })
    public ResponseEntity<OrderResponseDTO> getOrderDetail(@PathVariable Long id){
        return ResponseEntity.ok(orderUseCase.getOrderDetail(id));
    }

    @PostMapping
    @Operation(
        summary = "Crear una orden",
        description = "Crea una orden nueva asociada a un usuario con productos, cantidades y totales calculados",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            description = "Datos requeridos para registrar una orden",
            content = @Content(schema = @Schema(implementation = OrderRequestDTO.class))
        )
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Orden creada exitosamente",
            content = @Content(schema = @Schema(implementation = OrderResponseDTO.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos inválidos o error de stock",
            content = @Content(schema = @Schema(implementation = ErrorDetails.class))
        )
    })
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderRequestDTO orderRequestDTO){
        return new ResponseEntity<>(orderUseCase.createOrder(orderRequestDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar una orden existente",
        description = "Actualiza los productos, cantidades y totales de una orden creada",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            description = "Datos requeridos para actualizar una orden",
            content = @Content(schema = @Schema(implementation = OrderRequestDTO.class))
        )
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Orden actualizada correctamente",
            content = @Content(schema = @Schema(implementation = OrderResponseDTO.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Orden o producto no encontrado",
            content = @Content(schema = @Schema(implementation = ErrorDetails.class))
        )
    })
    public ResponseEntity<OrderResponseDTO> updateOrder(@RequestBody OrderRequestDTO orderRequestDTO, @PathVariable Long id){
        return ResponseEntity.ok(orderUseCase.updateOrder(orderRequestDTO, id));
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Eliminar orden (lógica)",
        description = "Marca como inactiva una orden y sus ítems asociados. No elimina físicamente los registros."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Orden eliminada correctamente"),
        @ApiResponse(
            responseCode = "404",
            description = "Orden no encontrada",
            content = @Content(schema = @Schema(implementation = ErrorDetails.class))
        )
    })
    public ResponseEntity<OrderResponseDTO> deleteOrder(@PathVariable Long id){
        orderUseCase.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/cancel/{id}")
    @Operation(
        summary = "Cancelar orden",
        description = "Cancela una orden y retorna el stock al inventario"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Orden cancelada exitosamente",
            content = @Content(schema = @Schema(implementation = OrderListResponseDTO.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Orden no encontrada",
            content = @Content(schema = @Schema(implementation = ErrorDetails.class))
        )
    })
    public ResponseEntity<OrderListResponseDTO> cancelOrder(@PathVariable Long id){
        return ResponseEntity.ok(orderUseCase.cancelOrder(id));
    }

}
