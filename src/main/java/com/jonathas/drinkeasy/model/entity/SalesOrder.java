package com.jonathas.drinkeasy.model.entity;

import com.jonathas.drinkeasy.enums.OrderStatus;
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

    // Construtores
    public SalesOrder(Date date, Client client) {
        this.date = date;
        this.client = client;
        this.status = OrderStatus.PENDING;
    }

    public SalesOrder(){
        this.status = OrderStatus.PENDING;
        this.products = new HashSet<>();
    }

    // Métodos de getter e setter
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

    // Método para calcular o valor total dos produtos
    public Double getTotalValue() {
        if (products == null || products.isEmpty()) {
            return 0.0;
        }
        return products.stream()
                .mapToDouble(OrderItem::calcSubtotal)
                .sum();
    }

    // Atualizar status do pedido
    public void updateStatus(OrderStatus newStatus){
        this.status = newStatus;
    }

    // Adicionar produto ao pedido
    public void addProduct(OrderItem product){
        this.products.add(product);
        this.totalValue += product.calcSubtotal();
    }

    // Remover produto do pedido
    public void removeProduct(OrderItem product){
        this.products.remove(product);
        this.totalValue -= product.calcSubtotal();
    }
}
