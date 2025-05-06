package com.group05.model;

import java.math.BigDecimal;

import com.group05.audit.ModelAudit;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "taxes", schema = "order_mgmt")
@Schema(name = "Tax", description = "Entidad que representa un impuesto aplicable a productos o servicios.")
public class Tax extends ModelAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del impuesto.", example = "1")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Nombre del impuesto.", example = "IGV")
    private String name;

    @Column(nullable = false, precision = 4, scale = 2)
    @Schema(description = "Tasa del impuesto expresada como decimal.", example = "0.1800")
    private BigDecimal rate;
}
