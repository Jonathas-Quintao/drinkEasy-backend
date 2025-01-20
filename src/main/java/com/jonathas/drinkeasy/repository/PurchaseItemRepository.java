package com.jonathas.drinkeasy.repository;

import com.jonathas.drinkeasy.model.entity.PurchaseItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PurchaseItemRepository extends JpaRepository<PurchaseItem, UUID> {
}
