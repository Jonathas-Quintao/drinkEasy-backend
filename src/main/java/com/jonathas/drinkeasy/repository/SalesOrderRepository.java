package com.jonathas.drinkeasy.repository;

import com.jonathas.drinkeasy.model.entity.SalesOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesOrderRepository extends JpaRepository<SalesOrder, Long> {
}
