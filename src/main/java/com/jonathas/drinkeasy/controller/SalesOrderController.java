package com.jonathas.drinkeasy.controller;

import com.jonathas.drinkeasy.model.dto.OrderItemDTO;
import com.jonathas.drinkeasy.model.dto.SalesOrderDTO;
import com.jonathas.drinkeasy.model.entity.OrderItem;
import com.jonathas.drinkeasy.model.entity.SalesOrder;
import com.jonathas.drinkeasy.service.SalesOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/sales-orders")
public class SalesOrderController {
    private final SalesOrderService salesOrderService;

    @Autowired
    public SalesOrderController(SalesOrderService salesOrderService){
        this.salesOrderService = salesOrderService;
    }

    @GetMapping
    public ResponseEntity<List<SalesOrderDTO>> findAll(){
        List<SalesOrderDTO> salesOrders = salesOrderService.findAll();
        return ResponseEntity.ok(salesOrders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalesOrderDTO> findById(@PathVariable("id")UUID id){
        Optional<SalesOrderDTO> salesOrder = salesOrderService.findById(id);
        return ResponseEntity.ok(salesOrder.get());
    }

    @PostMapping
    public ResponseEntity<SalesOrderDTO> post(@RequestBody SalesOrderDTO salesOrderDTO){
        Optional<SalesOrderDTO> salesOrder = Optional.ofNullable(salesOrderService.save(salesOrderDTO));
        return new ResponseEntity<>(salesOrder.get(), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/add-product")
    public ResponseEntity<SalesOrderDTO> addProductToOrder(
            @PathVariable UUID id,
            @RequestBody OrderItemDTO productDTO) {
        SalesOrderDTO updatedOrder = salesOrderService.addProduct(id, productDTO);
        return ResponseEntity.ok(updatedOrder);
    }

}
