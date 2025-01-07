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
    @ManyToOne
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;
    private Integer amount;
    private Double unitValue;

    public Double calcSubtotal(){
        return unitValue * amount;
    }
}
