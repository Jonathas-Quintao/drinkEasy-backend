package com.jonathas.drinkeasy.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private Integer price;
    private String category;
    private String brand;
    private String volume;
    private Integer currentStock;
    private Integer minimalStock;
    private Date expiration;
    @ManyToOne
    @JoinColumn(name = "stock_id")
    private Stock stock;


}
