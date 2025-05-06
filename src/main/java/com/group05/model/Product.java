package com.group05.model;

import java.util.ArrayList;
import java.util.List;

import com.group05.audit.AuditListener;
import com.group05.audit.ModelAudit;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name="products", schema="order_mgmt")
@Schema(name="Product", description = "Entidad que representa a un producto en el sistema")
@EntityListeners(AuditListener.class)
public class Product extends ModelAudit {

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

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @Schema(description = "Historial de precios del producto")
    private List<ProductPrice> prices = new ArrayList<>();

    @Schema(description = "Descripción del producto", example = "Producto tecnológico de alta gama")
    private String description;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @Schema(description = "Lista de impuestos aplicados al producto.")
    private List<ProductTax> productTaxes = new ArrayList<>();

}
