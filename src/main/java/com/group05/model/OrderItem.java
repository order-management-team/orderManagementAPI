package com.group05.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.group05.audit.ModelAudit;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name="order_items", schema = "order_mgmt")
@Schema(name = "OrderItem", description = "Entidad que representa un ítem dentro de un pedido.")
public class OrderItem extends ModelAudit{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del ítem de pedido", example = "1")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @Schema(description = "Producto asociado a un item del pedido")
    private Product product;
    
    @ManyToOne
    @JoinColumn(name = "order_id")
    @Schema(description = "Pedido al que pertenece este item")
    private Order order;

    @Schema(description = "cantidad del producto solicitado en el pedido", example = "3")
    private int quantity;

    @Schema(description = "Subtotal del ítem (precio unitario x cantidad)", example = "150.00")
    private BigDecimal subtotal;

    @ManyToOne
    @JoinColumn(name= "product_price_id", nullable = false)
    @Schema(description = "Precio histórico del producto aplicado en la orden", example = "1")
    private ProductPrice productPrice;

    @OneToMany(mappedBy = "orderItem", cascade = CascadeType.ALL)
    @Schema(description = "Lista de impuestos aplicados a este ítem al momento de la orden.")
    private List<OrderItemTax> taxes = new ArrayList<>();

}
