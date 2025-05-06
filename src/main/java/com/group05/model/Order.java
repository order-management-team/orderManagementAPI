package com.group05.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.group05.audit.ModelAudit;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
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
@Table(name="orders", schema = "order_mgmt")
@Schema(name="Order", description = "Entidad que representa a un pedido en el sistema")
public class Order extends ModelAudit{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del pedido", example = "1")
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    @Schema(description = "Fecha de registro del pedido", example = "08//04/2025 16:37:05")
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "state_id")
    @Schema(description = "Estado actual del pedido")
    private OrderState state;

    @ManyToOne
    @Schema(description = "Usuario que solicitó el pedido", example = "1")
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @Schema(description = "Lista de items incluidos en el pedido")
    private List<OrderItem> items;  

    @Schema(description = "Cantidad total")
    @Column(name="total_amount")
    private BigDecimal totalAmount;

    @PrePersist
    public void prePersist() {
        this.date = LocalDateTime.now();
    }
}
