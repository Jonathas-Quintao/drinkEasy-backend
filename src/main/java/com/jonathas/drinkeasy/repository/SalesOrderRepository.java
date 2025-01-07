package com.jonathas.drinkeasy.repository;

import com.jonathas.drinkeasy.model.entity.SalesOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SalesOrderRepository extends JpaRepository<SalesOrder, UUID> {
}
