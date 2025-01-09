package com.jonathas.drinkeasy.controller;

import com.jonathas.drinkeasy.model.dto.ProductDTO;
import com.jonathas.drinkeasy.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> findAll(){
        List<ProductDTO> products = productService.findAll();
        return ResponseEntity.ok(products);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable("id")UUID id){
        Optional<ProductDTO> product = productService.findById(id);
        return ResponseEntity.ok(product.get());
    }
    @PostMapping
    public ResponseEntity<ProductDTO> post(@RequestBody ProductDTO productDTO){
        Optional<ProductDTO> product = Optional.ofNullable(productService.save(productDTO));
        return new ResponseEntity<>(product.get(), HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> put(@PathVariable("id") UUID id, @RequestBody ProductDTO productDTO){
        ProductDTO updatedProduct = productService.update(id, productDTO);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable UUID id){
        productService.delete(id);;
        return ResponseEntity.noContent().build();
    }
}
