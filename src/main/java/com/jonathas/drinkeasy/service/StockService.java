package com.jonathas.drinkeasy.service;

import com.jonathas.drinkeasy.exceptions.StockNotFoundException;
import com.jonathas.drinkeasy.model.dto.StockDTO;
import com.jonathas.drinkeasy.model.entity.Stock;
import com.jonathas.drinkeasy.repository.StockRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    public List<StockDTO> findAll(){
        List<Stock> stocks = stockRepository.findAll();

        return stocks.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<StockDTO> findById(@NotNull UUID id){
        if(id == null){
            throw new IllegalArgumentException("The given id must not be null");
        }
        Optional<Stock> stock = stockRepository.findById(id);
        if(!stock.isPresent()){
            throw new StockNotFoundException("Stock not found");
        }
        return Optional.of(convertToDTO(stock.get()));
    }

    @Transactional
    public StockDTO update(UUID id, StockDTO stockDTO){
        Optional<Stock> optionalStock = stockRepository.findById(id);

        if(!optionalStock.isPresent()){
            throw new StockNotFoundException("Stock not found");
        }
        Stock existingStock = optionalStock.get();

        existingStock.setName(stockDTO.getName());
        existingStock.setStockProducts(stockDTO.getStockProducts());

        Stock updatedStock = stockRepository.save(existingStock);
        return convertToDTO(updatedStock);
    }

    @Transactional
    public void delete(UUID id){
        Optional<Stock> stock = stockRepository.findById(id);
        if(!stock.isPresent()){
            throw new StockNotFoundException("Stock not found");
        }
        stockRepository.delete(stock.get());
    }

    @Transactional
    public StockDTO save(StockDTO stockDTO){
        Stock stock = create(stockDTO);

        Stock savedStock = stockRepository.save(stock);
        return convertToDTO(savedStock);
    }

    public Stock create(StockDTO stockDTO){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(stockDTO, Stock.class);
    }

    private StockDTO convertToDTO(Stock stock){
        StockDTO stockDTO = new StockDTO();
        stockDTO.setId(stock.getId());
        stockDTO.setName(stock.getName());
        stockDTO.setStockProducts(stock.getStockProducts());
        return stockDTO;

    }
}
