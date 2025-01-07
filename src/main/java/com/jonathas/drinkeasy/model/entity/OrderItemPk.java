package com.jonathas.drinkeasy.model.entity;

import jakarta.persistence.Embeddable;
import java.util.UUID;

@Embeddable
public class OrderItemPk {

    private UUID productId;
    private UUID salesOrderId;
}
