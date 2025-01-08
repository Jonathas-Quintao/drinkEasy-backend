package com.jonathas.drinkeasy.repository;

import com.jonathas.drinkeasy.model.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SupplierRepository extends JpaRepository<Supplier, UUID> {
}
