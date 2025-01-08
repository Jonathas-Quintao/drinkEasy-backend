package com.jonathas.drinkeasy.controller;


import com.jonathas.drinkeasy.model.dto.SupplierDTO;
import com.jonathas.drinkeasy.service.SupplierService;
import jdk.javadoc.doclet.Reporter;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    @GetMapping("/{id}")
    public ResponseEntity<SupplierDTO> findById(@PathVariable("id") UUID id){
        Optional<SupplierDTO> supplier = supplierService.findById(id);
        return ResponseEntity.ok(supplier.get());
    }

    @PostMapping
    public ResponseEntity<SupplierDTO> post(@RequestBody SupplierDTO supplierDTO){
        Optional<SupplierDTO> supplier = Optional.ofNullable(supplierService.save(supplierDTO));
        return new ResponseEntity<>(supplier.get(), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupplierDTO> put(@PathVariable("id") UUID id, @RequestBody SupplierDTO supplierDTO){
        SupplierDTO updatedSupplier = supplierService.update(id, supplierDTO);
        return ResponseEntity.ok(updatedSupplier);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable UUID id){
        supplierService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
