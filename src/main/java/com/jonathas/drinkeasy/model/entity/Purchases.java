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
@AllArgsConstructor
@Table(name = "tb_Purchases")
public class Purchases {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private List<PurchaseOrder> listPurchaseOrder;

    public Purchases(){
        this.listPurchaseOrder = new ArrayList<>();
    }
    public void addPurchaseOrder(PurchaseOrder order) {
        listPurchaseOrder.add(order);
    }

    public List<PurchaseOrder> getListPurchaseOrder() {
        return listPurchaseOrder;
    }
}
