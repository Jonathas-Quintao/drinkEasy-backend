package com.jonathas.drinkeasy.model.entity;

import com.jonathas.drinkeasy.enums.OrderStatus;
import com.jonathas.drinkeasy.model.dto.OrderItemDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Entity
@Data
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
    @OneToMany(mappedBy = "id.salesOrder", cascade = CascadeType.ALL)
    private Set<OrderItem> products = new HashSet<>();
    private Double totalValue = 0.0;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;


    public SalesOrder(Date date, Client client) {
        this.date = date;
        this.client = client;
        this.status = OrderStatus.PENDING;
    }

    public SalesOrder(){
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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set<OrderItem> getProducts() {
        return products;
    }

    public void setProducts(Set<OrderItem> products) {
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
                .mapToDouble(OrderItem::calcSubtotal)
                .sum();
    }


    public void updateStatus(OrderStatus newStatus){
        this.status = newStatus;
    }


    public void addProduct(OrderItem product){
       Optional<OrderItem> existingItem = this.products.stream()
               .filter(orderItem -> orderItem.getId().equals(product.getId()))
               .findFirst();

       if(existingItem.isPresent()){
           OrderItem item = existingItem.get();
           item.setAmount(item.getAmount() + product.getAmount());
           item.setUnitValue(product.getUnitValue());
       }else{
           this.products.add(product);
       }
    }


    public void removeProduct(OrderItem product){
        this.products.remove(product);
        this.totalValue -= product.calcSubtotal();
    }
}
