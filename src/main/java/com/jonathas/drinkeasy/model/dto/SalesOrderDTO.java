package com.jonathas.drinkeasy.model.dto;

import com.jonathas.drinkeasy.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesOrderDTO {

    private UUID id;
    private Date date;
    private UUID clientId;
    private String clientName;
    private List<OrderItemDTO> products;
    private Double totalValue;
    private OrderStatus status;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public UUID getClientId() {
        return clientId;
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public List<OrderItemDTO> getProducts() {
        return products;
    }

    public void setProducts(List<OrderItemDTO> products) {
        this.products = products;
    }

    public void setTotalValue(Double totalValue) {
        this.totalValue = totalValue;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Double getTotalValue() {
        if (products == null || products.isEmpty()) {
            return 0.0;
        }
        return products.stream()
                .mapToDouble(product -> product.getUnitValue() * product.getAmount())
                .sum();
    }
}
