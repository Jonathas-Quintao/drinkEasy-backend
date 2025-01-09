package com.jonathas.drinkeasy.repository;

import com.jonathas.drinkeasy.model.entity.StockProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StockProductRepository extends JpaRepository<StockProduct, UUID> {
}
