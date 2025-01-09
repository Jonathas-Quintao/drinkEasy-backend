package com.jonathas.drinkeasy.controller;

import com.jonathas.drinkeasy.model.dto.StockDTO;
import com.jonathas.drinkeasy.service.StockService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/stocks")
public class StockController {
    private final StockService stockService;

    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping
    public ResponseEntity<List<StockDTO>> findAll() {
        List<StockDTO> stocks = stockService.findAll();
        return ResponseEntity.ok(stocks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockDTO> findById(@PathVariable("id")UUID id){
        Optional<StockDTO> stock = stockService.findById(id);
        return ResponseEntity.ok(stock.get());
    }

    @PostMapping
    public ResponseEntity<StockDTO> post(@RequestBody StockDTO stockDTO){
        Optional<StockDTO> stock = Optional.ofNullable(stockService.save(stockDTO));
        return new ResponseEntity<>(stock.get(), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StockDTO> put(@PathVariable("id") UUID id, @RequestBody StockDTO stockDTO){
        StockDTO updatedStock = stockService.update(id, stockDTO);
        return ResponseEntity.ok(updatedStock);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable UUID id){
        stockService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
