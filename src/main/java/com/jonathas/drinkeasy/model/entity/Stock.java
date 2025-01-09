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


    @OneToMany(mappedBy = "stock", cascade = CascadeType.ALL, orphanRemoval = true)

    private List<StockProduct> stockProducts = new ArrayList<>();

public void addProduct(StockProduct product){
    this.stockProducts.add(product);
}

public void removeProduct(StockProduct product){
    this.stockProducts.remove(product);
}

public List<StockProduct> checkStock(){
    return this.stockProducts;
}

//public Boolean checkSlowStock(Product product){
//    return product.getCurrentStock() <= product.getMinimalStock();
//}
}
