package com.jonathas.drinkeasy.service;

import com.jonathas.drinkeasy.exceptions.SupplierNotFoundException;
import com.jonathas.drinkeasy.model.dto.SupplierDTO;
import com.jonathas.drinkeasy.model.entity.Client;
import com.jonathas.drinkeasy.model.entity.Supplier;
import com.jonathas.drinkeasy.repository.SupplierRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;
    public List<SupplierDTO> findAll(){
        List<Supplier> suppliers = supplierRepository.findAll();

        return suppliers.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public SupplierDTO findById(UUID id){
        Optional<Supplier> supplier = supplierRepository.findById(id);
        if(!supplier.isPresent()){
            throw new SupplierNotFoundException("Supplier not found!");
        }
        return convertToDTO(supplier.get());
    }

    @Transactional
    public void delete(UUID id){
        Optional<Supplier> supplier = supplierRepository.findById(id);

        if(!supplier.isPresent()){
            throw new SupplierNotFoundException("Supplier not found");
        }
        supplierRepository.delete(supplier.get());
    }

    @Transactional
    public SupplierDTO save(SupplierDTO supplierDTO){
        Supplier supplier = create(supplierDTO);

        Supplier savedSupplier = supplierRepository.save(supplier);

        return convertToDTO(savedSupplier);
    }





    private SupplierDTO convertToDTO(Supplier supplier){
        SupplierDTO supplierDTO = new SupplierDTO();
        supplierDTO.setId(supplier.getId());
        supplierDTO.setName(supplier.getName());
        supplierDTO.setCnpj(supplier.getCnpj());
        supplierDTO.setPhone(supplier.getPhone());
        supplierDTO.setAddress(supplier.getAddress());

        return supplierDTO;
    }

    private Supplier create(SupplierDTO supplierDTO){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(supplierDTO, Supplier.class);
    }
}
