package com.jonathas.drinkeasy.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_orderitem")
public class OrderItem {
    @EmbeddedId
    private OrderItemPk id;

    private Integer amount;
    private Double unitValue;

    @ManyToOne
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "sales_order_id", insertable = false, updatable = false)
    private SalesOrder salesOrder;

    public Double calcSubtotal(){
        return unitValue * amount;
    }
}
