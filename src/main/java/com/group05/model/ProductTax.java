package com.group05.model;

import com.group05.audit.ModelAudit;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "product_taxes", schema = "order_mgmt")
@Schema(name = "ProductTax", description = "Entidad que representa la relación entre un producto y un impuesto aplicado.")
public class ProductTax extends ModelAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único de la relación producto-impuesto.", example = "1")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @Schema(description = "Producto al que se le aplica el impuesto.")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "tax_id", nullable = false)
    @Schema(description = "Impuesto aplicado al producto.")
    private Tax tax;
}