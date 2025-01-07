package com.jonathas.drinkeasy.repository;

import com.jonathas.drinkeasy.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
