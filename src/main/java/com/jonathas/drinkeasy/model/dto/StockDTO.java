package com.jonathas.drinkeasy.model.dto;

import com.jonathas.drinkeasy.model.entity.StockProduct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockDTO {

    private UUID id;
    private String name;
    private List<StockProduct> stockProducts;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<StockProduct> getStockProducts() {
        return stockProducts;
    }

    public void setStockProducts(List<StockProduct> stockProducts) {
        this.stockProducts = stockProducts;
    }
}
