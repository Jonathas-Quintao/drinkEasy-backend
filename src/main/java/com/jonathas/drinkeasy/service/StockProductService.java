package com.jonathas.drinkeasy.service;

import com.jonathas.drinkeasy.model.dto.StockProductDTO;
import com.jonathas.drinkeasy.model.entity.StockProduct;
import com.jonathas.drinkeasy.repository.StockProductRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class StockProductService {
    @Autowired
    private StockProductRepository stockProductRepository;

    @Transactional
    public StockProductDTO save(StockProductDTO stockProductDTO){
        StockProduct stockProduct = create(stockProductDTO);

        StockProduct savedStockProduct = stockProductRepository.save(stockProduct);

        return convertToDTO(savedStockProduct);

    }

    public StockProduct create(StockProductDTO stockProductDTO){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(stockProductDTO, StockProduct.class);
    }

    public StockProductDTO convertToDTO(StockProduct stockProduct){
        StockProductDTO stockProductDTO = new StockProductDTO();

        stockProductDTO.setStockId(stockProduct.getStock().getId());
        stockProductDTO.setProductId(stockProduct.getProduct().getId());
        stockProductDTO.setAmount(stockProduct.getAmount());

        return stockProductDTO;
    }

}
