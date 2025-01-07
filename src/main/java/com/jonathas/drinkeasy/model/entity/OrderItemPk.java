package com.jonathas.drinkeasy.model.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.UUID;
@Data
@Embeddable
public class OrderItemPk {

    @ManyToOne
    @JoinColumn(name = "salesorder_id")
    private SalesOrder salesOrder;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
