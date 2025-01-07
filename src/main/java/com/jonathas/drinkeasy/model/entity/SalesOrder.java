package com.jonathas.drinkeasy.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_SalesOrder")
public class SalesOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Date date;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    @OneToMany(mappedBy = "salesOrder")
    private Set<OrderItem> products = new HashSet<>();
    private Double totalValue = 0.0;
    private String status;

    public Double calcTotalValue(){
        for(OrderItem a : products){
         this.totalValue += a.calcSubtotal();
        }
        return totalValue;
    }

    public void updateStatus(String newStatus){
        this.status = newStatus;
    }
}
