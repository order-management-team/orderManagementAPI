package com.group05.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="products", schema="order_mgmt")
@Schema(name="products", description = "Entidad que representa a un producto en el sistema")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del producto", example="1")
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Column(unique = true, nullable = false)
    @Schema(description = "Nombre del producto", example="Laptop")
    private String name;

    @Min(value = 0, message = "El stock no puede ser negativo")
    @Column(nullable = false)
    @Schema(description = "Cantidad de stock del producto", example = "10")
    private Long stock;

    @DecimalMin(value = "0.01", inclusive = true, message = "El precio debe ser mayor a cero")
    @Column(nullable = false)
    @Schema(description = "Precio del producto", example = "1500.50")
    private Double price;

    @Schema(description = "Descripción del producto", example = "Producto tecnológico de alta gama")
    private String description;
}
