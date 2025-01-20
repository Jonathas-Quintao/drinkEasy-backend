package com.jonathas.drinkeasy.controller;

import com.jonathas.drinkeasy.model.dto.PurchaseOrderDTO;
import com.jonathas.drinkeasy.service.PurchaseOrderService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    @GetMapping("/{id}")
    public ResponseEntity<PurchaseOrderDTO> findById(@PathVariable("id") UUID id){
        Optional<PurchaseOrderDTO> purchaseOrder = purchaseOrderService.findById(id);
        return ResponseEntity.ok(purchaseOrder.get());
    }
    @PostMapping
    public ResponseEntity<PurchaseOrderDTO> post(@RequestBody PurchaseOrderDTO purchaseOrderDTO){
        Optional<PurchaseOrderDTO> purchaseOrder = Optional.ofNullable((purchaseOrderService.save(purchaseOrderDTO)));
        return new ResponseEntity<>(purchaseOrder.get(), HttpStatus.CREATED);
    }
}
