package com.group05.model;

import java.math.BigDecimal;

import com.group05.audit.ModelAudit;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
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
@Table(name = "order_item_taxes", schema = "order_mgmt")
@Schema(name = "OrderItemTax", description = "Detalle de impuesto aplicado a un ítem de orden.")
public class OrderItemTax extends ModelAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del impuesto aplicado al ítem.", example = "1")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_item_id", nullable = false)
    @Schema(description = "Ítem de la orden al que se le aplicó el impuesto.")
    private OrderItem orderItem;

    @Column(nullable = false)
    @Schema(description = "Nombre del impuesto aplicado.", example = "IGV")
    private String taxName;

    @Column(nullable = false, precision = 4, scale = 2)
    @Schema(description = "Tasa aplicada del impuesto.", example = "0.18")
    private BigDecimal rate;

    @Column(nullable = false, precision = 10, scale = 2)
    @Schema(description = "Monto total del impuesto calculado para este ítem.", example = "18.00")
    private BigDecimal amount;
}
