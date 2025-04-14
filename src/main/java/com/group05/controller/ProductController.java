package com.group05.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group05.dto.ProductRequestDTO;
import com.group05.dto.ProductResponseDTO;
import com.group05.exceptionHandler.ErrorDetails;
import com.group05.service.use_cases.ProductUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
@Tag(name="Productos", description = "Servicios relacionados a la gestión de productos")
public class ProductController {

    private ProductUseCase productUseCase;

    public ProductController(ProductUseCase productUseCase){
        this.productUseCase = productUseCase;
    }

    @GetMapping
    @Operation(
        summary = "Listar productos",
        description = "Servicio encargado de listar todos los productos"

    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Lista de productos",
            content = @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = ProductResponseDTO.class))
            )
        )
    })
    public ResponseEntity<List<ProductResponseDTO>> getProducts(){
        return ResponseEntity.ok(productUseCase.getProducts());
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Mostrar detalle del producto",
        description = "Servicio encargado de mostrar el detalle de un producto según su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Producto encontrado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ProductResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Producto no encontrado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorDetails.class)
            )
        )
    })
    public ResponseEntity<ProductResponseDTO> getProductDetail(
        @Parameter(description = "Filtro de producto", required = true, in = ParameterIn.PATH) 
        @PathVariable Long id) {
        return ResponseEntity.ok(productUseCase.getProductDetail(id));
    }

    @PostMapping
    @Operation(
        summary = "Registrar productos", 
        description = "Servicio encargado de registar productos",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos requeridos para registrar un producto en el sistema",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ProductRequestDTO.class)
            )
        )
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Producto registrado exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ProductResponseDTO.class)
            )
        )
    })
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductRequestDTO productRequestDTO){
        return new ResponseEntity<>(productUseCase.createProduct(productRequestDTO), HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar producto",
        description = "Servicio encargado de actualizar un producto existente en el sistema",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos requeridos para actualizar un producto",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ProductRequestDTO.class)
            )
        )
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Producto actualizado correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ProductResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Producto no encontrado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorDetails.class)
            )
        )
    })
    public ResponseEntity<ProductResponseDTO> updateProduct(@Valid @RequestBody ProductRequestDTO productRequestDTO, @PathVariable Long id){
        return ResponseEntity.ok(productUseCase.updateProduct(productRequestDTO, id));
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Eliminar producto por ID",
        description = "Elimina un producto existente usando su ID. Retorna 204 si se elimina correctamente o 404 si no se encuentra."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204",
            description = "Producto eliminado correctamente"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Producto no encontrado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorDetails.class)
            )
        )
    })
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        productUseCase.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

}
