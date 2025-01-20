package com.jonathas.drinkeasy.controller;

import com.jonathas.drinkeasy.model.dto.PurchaseOrderDTO;
import com.jonathas.drinkeasy.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/purchase-orders")
public class PurchaseOrderController {
    private final PurchaseOrderService purchaseOrderService;

    @Autowired
    public PurchaseOrderController(PurchaseOrderService purchaseOrderService){
        this.purchaseOrderService = purchaseOrderService;
    }

    @GetMapping
    public ResponseEntity<List<PurchaseOrderDTO>> findAll(){
        List<PurchaseOrderDTO> purchaseOrders = purchaseOrderService.findAll();
        return ResponseEntity.ok(purchaseOrders);
    }
}
