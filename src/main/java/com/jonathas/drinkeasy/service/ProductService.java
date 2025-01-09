package com.jonathas.drinkeasy.service;

import com.jonathas.drinkeasy.exceptions.ProductNotFoundException;
import com.jonathas.drinkeasy.model.dto.ProductDTO;
import com.jonathas.drinkeasy.model.entity.Product;
import com.jonathas.drinkeasy.repository.ProductRepository;
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
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<ProductDTO> findAll(){
        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<ProductDTO> findById(@NotNull UUID id){
        if(id == null){
            throw new IllegalArgumentException("The given id must not be null");
        }
        Optional<Product> product = productRepository.findById(id);
        if(!product.isPresent()){
            throw new ProductNotFoundException("Product not found!");
        }
        return Optional.of(convertToDTO(product.get()));
    }

    @Transactional
    public ProductDTO update(UUID id, ProductDTO productDTO){
        Optional<Product> optionalProduct = productRepository.findById(id);

        if(!optionalProduct.isPresent()){
            throw new ProductNotFoundException("Product not found");
        }

        Product existingProduct = optionalProduct.get();

        existingProduct.setName(productDTO.getName());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setCategory(productDTO.getCategory());
        existingProduct.setBrand(productDTO.getBrand());
        existingProduct.setVolume(productDTO.getVolume());
        existingProduct.setCurrentStock(productDTO.getCurrentStock());
        existingProduct.setMinimalStock(productDTO.getMinimalStock());
        existingProduct.setExpiration(productDTO.getExpiration());


        Product updatedProduct = productRepository.save(existingProduct);

        return convertToDTO(updatedProduct);
    }

    @Transactional
    public ProductDTO save(ProductDTO productDTO){
        Product product = create(productDTO);
        Product savedProduct = productRepository.save(product);
        return convertToDTO(savedProduct);
    }

    public Product create(ProductDTO productDTO){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(productDTO, Product.class);
    }

    @Transactional
    public void delete(UUID id){
        Optional<Product> product = productRepository.findById(id);
        if(!product.isPresent()){
            throw new ProductNotFoundException("Product not found");
        }

        productRepository.delete(product.get());;
    }



    private ProductDTO convertToDTO(Product product){
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        productDTO.setCategory(product.getCategory());
        productDTO.setBrand(product.getBrand());
        productDTO.setVolume(product.getVolume());
        productDTO.setCurrentStock(product.getCurrentStock());
        productDTO.setMinimalStock(product.getMinimalStock());
        productDTO.setExpiration(product.getExpiration());


        return productDTO;


    }
}
