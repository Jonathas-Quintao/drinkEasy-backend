package com.jonathas.drinkeasy.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_stock")
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    @OneToMany(mappedBy = "stock", cascade = CascadeType.ALL)

    private List<StockProduct> stockProducts = new ArrayList<>();

public void addProduct(StockProduct product){
    this.stockProducts.add(product);
}

public void removeProduct(StockProduct product){
    this.stockProducts.remove(product);
}

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

    public List<StockProduct> checkStock(){
    return this.stockProducts;
}

//public Boolean checkSlowStock(Product product){
//    return product.getCurrentStock() <= product.getMinimalStock();
//}
}
