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
    private Integer amount;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "stock_id")
    private List<Product> productList = new ArrayList<>();

public void addProduct(Product product){
    this.productList.add(product);
}

public void removeProduct(Product product){
    this.productList.remove(product);
}

public List<Product> checkStock(){
    return this.productList;
}

//public Boolean checkSlowStock(Product product){
//    return product.getCurrentStock() <= product.getMinimalStock();
//}
}
