package com.group05.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.group05.audit.ModelAudit;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "product_prices", schema = "order_mgmt")
@Schema(name = "ProductPrice", description = "Entidad que guarda el historial de precios de un producto.")
public class ProductPrice extends ModelAudit{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del historial de precio", example = "1")
    private Long id;

    @ManyToOne
    @JoinColumn(name="product_id", nullable = false)
    @Schema(description = "Producto asociado a los precios")
    private Product product;

    @DecimalMin(value = "0.01", inclusive = true, message = "El precio debe ser mayor a cero")
    @Column(nullable = false)
    @Schema(description = "Precio del producto", example = "349.99")
    private BigDecimal price;

    @Column(nullable = false)
    @NotNull
    @Schema(description = "Último precio vigente activo", example = "349.99")
    private boolean activePrice;

    @Column(nullable = false)
    @NotNull
    @Schema(description = "Fecha desde la cual entra en vigencia el precio", example = "18/04/2025")
    private LocalDate effectiveFrom;

    @Schema(description = "Fecha hasta la cual es válido el precio", example = "01/05/2025")
    private LocalDate effectiveTo;
    
    @PrePersist
    public void prePersist() {
            this.effectiveFrom = LocalDate.now();
    }
}
