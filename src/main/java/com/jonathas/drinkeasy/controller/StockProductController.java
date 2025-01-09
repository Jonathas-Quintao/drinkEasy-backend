package com.jonathas.drinkeasy.controller;

import com.jonathas.drinkeasy.model.dto.StockProductDTO;
import com.jonathas.drinkeasy.service.StockProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/stock-products")
public class StockProductController {
    private final StockProductService stockProductService;

    @Autowired
    public StockProductController(StockProductService stockProductService){
        this.stockProductService = stockProductService;
    }

    @PostMapping
    public ResponseEntity<StockProductDTO> post(@RequestBody StockProductDTO stockProductDTO){
        Optional<StockProductDTO> stockProduct = Optional.ofNullable(stockProductService.save(stockProductDTO));
        return new ResponseEntity<>(stockProduct.get(), HttpStatus.CREATED);
    }

}
