package com.jonathas.drinkeasy.model.entity;

import com.jonathas.drinkeasy.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_PurchaseOrder")
public class PurchaseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Date date;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @OneToMany(mappedBy = "id.purchaseOrder", cascade = CascadeType.ALL)
    private Set<PurchaseItem> products = new HashSet<>();
    private Double totalValue = 0.0;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public PurchaseOrder(Date date, Supplier supplier){
        this.date = date;
        this.supplier = supplier;
        this.status = OrderStatus.PENDING;
    }

    public PurchaseOrder(){
        this.status = OrderStatus.PENDING;
        this.products = new HashSet<>();
    }

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

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Set<PurchaseItem> getProducts() {
        return products;
    }

    public void setProducts(Set<PurchaseItem> products) {
        this.products = products;
    }

    public Double getTotalValue() {
        if (products == null || products.isEmpty()) {
            return 0.0;
        }
        return products.stream()
                .mapToDouble(PurchaseItem::calcSubtotal)
                .sum();
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
    public void updateStatus(OrderStatus newStatus){
        this.status = newStatus;
    }


    public void addProduct(PurchaseItem product) {
        Optional<PurchaseItem> existingItem = this.products.stream()
                .filter(item -> item.getId().equals(product.getId()))
                .findFirst();

        if (existingItem.isPresent()) {
            PurchaseItem item = existingItem.get();
            item.setAmount(item.getAmount() + product.getAmount());
            item.setUnitValue(product.getUnitValue());
        } else {
            this.products.add(product);
        }

        this.totalValue = this.products.stream()
                .mapToDouble(PurchaseItem::calcSubtotal)
                .sum();
    }


    public void removeProduct(PurchaseItem product){
        this.products.remove(product);
        this.totalValue -= product.calcSubtotal();
    }
}
