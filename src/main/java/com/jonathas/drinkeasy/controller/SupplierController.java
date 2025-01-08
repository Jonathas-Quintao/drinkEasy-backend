package com.jonathas.drinkeasy.controller;


import com.jonathas.drinkeasy.model.dto.SupplierDTO;
import com.jonathas.drinkeasy.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/suppliers")
public class SupplierController {
    private final SupplierService supplierService;

    @Autowired
    public SupplierController(SupplierService supplierService){
        this.supplierService = supplierService;
    }

    @GetMapping
    public ResponseEntity<List<SupplierDTO>> findAll(){
        List<SupplierDTO> suppliers = supplierService.findAll();
        return ResponseEntity.ok(suppliers);
    }

    @PostMapping
    public ResponseEntity<SupplierDTO> post(@RequestBody SupplierDTO supplierDTO){
        Optional<SupplierDTO> supplier = Optional.ofNullable(supplierService.save(supplierDTO));
        return new ResponseEntity<>(supplier.get(), HttpStatus.CREATED);
    }


}
