package com.jonathas.drinkeasy.repository;

import com.jonathas.drinkeasy.model.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {
}
