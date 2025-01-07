package com.jonathas.drinkeasy.repository;

import com.jonathas.drinkeasy.model.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
