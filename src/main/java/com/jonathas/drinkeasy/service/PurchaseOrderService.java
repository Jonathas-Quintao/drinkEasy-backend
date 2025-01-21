package com.jonathas.drinkeasy.service;

import com.jonathas.drinkeasy.exceptions.PurchaseOrderNotFoundException;
import com.jonathas.drinkeasy.model.dto.OrderItemDTO;
import com.jonathas.drinkeasy.model.dto.PurchaseItemDTO;
import com.jonathas.drinkeasy.model.dto.PurchaseOrderDTO;
import com.jonathas.drinkeasy.model.entity.*;
import com.jonathas.drinkeasy.repository.ProductRepository;
import com.jonathas.drinkeasy.repository.PurchaseItemRepository;
import com.jonathas.drinkeasy.repository.PurchaseOrderRepository;
import com.jonathas.drinkeasy.repository.SupplierRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PurchaseOrderService {
    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private PurchaseItemRepository purchaseItemRepository;

    public List<PurchaseOrderDTO> findAll(){
        List<PurchaseOrder> purchaseOrders = purchaseOrderRepository.findAll();

        return purchaseOrders.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<PurchaseOrderDTO> findById(@NotNull UUID id){
        if(id == null){
            throw new IllegalArgumentException("The given id must not be null");
        }

        Optional<PurchaseOrder> purchaseOrder = purchaseOrderRepository.findById((id));
        if(!purchaseOrder.isPresent()){
            throw new PurchaseOrderNotFoundException("Purchase Order not found!");
        }
        return Optional.of(convertToDTO(purchaseOrder.get()));
    }

    @Transactional
    public PurchaseOrderDTO addProduct(UUID purchaseOrderId, PurchaseItemDTO productDTO) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(purchaseOrderId)
                .orElseThrow(() -> new RuntimeException("Purchase Order not found."));

        Product product = productRepository.findById(productDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found."));


        Optional<PurchaseItem> existingItem = purchaseOrder.getProducts().stream()
                .filter(item -> item.getId().getProduct().getId().equals(productDTO.getProductId()))
                .findFirst();

        if (existingItem.isPresent()) {

            PurchaseItem item = existingItem.get();
            item.setAmount(item.getAmount() + productDTO.getAmount());
            item.setUnitValue(productDTO.getUnitValue());
        } else {

            PurchaseItem purchaseItem = new PurchaseItem();
            PurchaseItemPk purchaseItemPk = new PurchaseItemPk();
            purchaseItemPk.setPurchaseOrder(purchaseOrder);
            purchaseItemPk.setProduct(product);

            purchaseItem.setId(purchaseItemPk);
            purchaseItem.setAmount(productDTO.getAmount());
            purchaseItem.setUnitValue(productDTO.getUnitValue());

            purchaseOrder.getProducts().add(purchaseItem);
        }


        double totalValue = purchaseOrder.getProducts().stream()
                .mapToDouble(PurchaseItem::calcSubtotal)
                .sum();
        purchaseOrder.setTotalValue(totalValue);


        purchaseOrderRepository.save(purchaseOrder);

        return convertToDTO(purchaseOrder);
    }

    @Transactional
    public PurchaseOrderDTO removeProduct(UUID id, PurchaseItemDTO productDTO){
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new PurchaseOrderNotFoundException("Purchase Order not found."));

        PurchaseItem productToRemove = purchaseOrder.getProducts().stream()
                .filter(item -> item.getId().getProduct().getId().equals(productDTO.getProductId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found in Purchase Order."));

        if(productDTO.getAmount() >= productToRemove.getAmount()){
            purchaseOrder.getProducts().remove(productToRemove);
        }else{
            productToRemove.setAmount(productToRemove.getAmount() - productDTO.getAmount());
        }


        double totalValue = purchaseOrder.getProducts().stream()
                .mapToDouble(PurchaseItem::calcSubtotal)
                .sum();
        purchaseOrder.setTotalValue(totalValue);

        purchaseOrderRepository.save(purchaseOrder);
        return convertToDTO(purchaseOrder);
    }

    @Transactional
    public PurchaseOrderDTO update(UUID id, PurchaseOrderDTO purchaseOrderDTO){
        Optional<PurchaseOrder> optionalPurchaseOrder = purchaseOrderRepository.findById(id);

        if (!optionalPurchaseOrder.isPresent()) {
            throw new PurchaseOrderNotFoundException("Purchase Order not found!");
        }
        PurchaseOrder existingPurchaseOrder = optionalPurchaseOrder.get();

        existingPurchaseOrder.setDate(purchaseOrderDTO.getDate());
        existingPurchaseOrder.setStatus(purchaseOrderDTO.getStatus());

        Supplier supllier = supplierRepository.findById(purchaseOrderDTO.getSupplierId())
                .orElseThrow(() -> new RuntimeException("Supplier not found."));
        existingPurchaseOrder.setSupplier(supllier);

        Set<PurchaseItem> updatedItems = purchaseOrderDTO.getProducts().stream().map(itemDTO -> {
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado."));

            PurchaseItem purchaseItem = new PurchaseItem();
            PurchaseItemPk purchaseItemPk = new PurchaseItemPk();
            purchaseItemPk.setPurchaseOrder(existingPurchaseOrder);
            purchaseItemPk.setProduct(product);

            purchaseItem.setId(purchaseItemPk);
            purchaseItem.setAmount(itemDTO.getAmount());
            purchaseItem.setUnitValue(itemDTO.getUnitValue());
            return purchaseItem;

        }).collect(Collectors.toSet());

        existingPurchaseOrder.setProducts(updatedItems);

        double totalValue = updatedItems.stream()
                .mapToDouble(PurchaseItem::calcSubtotal)
                .sum();
        existingPurchaseOrder.setTotalValue(totalValue);

        PurchaseOrder updatedPurchaseOrder = purchaseOrderRepository.save(existingPurchaseOrder);

        return convertToDTO(updatedPurchaseOrder);

    }

    @Transactional
    public void delete(UUID id){
        Optional<PurchaseOrder> purchaseOrder = purchaseOrderRepository.findById(id);

        if(!purchaseOrder.isPresent()){
            throw new PurchaseOrderNotFoundException("Purchase Order not found!");
        }
        purchaseOrderRepository.delete(purchaseOrder.get());
    }

    @Transactional
    public PurchaseOrderDTO save(PurchaseOrderDTO purchaseOrderDTO){
        PurchaseOrder purchaseOrder = create(purchaseOrderDTO);
        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        return convertToDTO(savedPurchaseOrder);
    }

    public PurchaseOrder create(PurchaseOrderDTO purchaseOrderDTO){
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setDate(purchaseOrderDTO.getDate());
        purchaseOrder.setStatus(purchaseOrderDTO.getStatus());

        Supplier supplier = supplierRepository.findById(purchaseOrderDTO.getSupplierId())
                .orElseThrow(() -> new RuntimeException("Supplier not found."));
        purchaseOrder.setSupplier(supplier);

        Set<PurchaseItem> purchaseItems = purchaseOrderDTO.getProducts().stream().map(itemDTO -> {
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found."));

            PurchaseItem purchaseItem = new PurchaseItem();
            PurchaseItemPk purchaseItemPk = new PurchaseItemPk();
            purchaseItemPk.setPurchaseOrder(purchaseOrder);
            purchaseItemPk.setProduct(product);

            purchaseItem.setId(purchaseItemPk);
            purchaseItem.setAmount(itemDTO.getAmount());
            purchaseItem.setUnitValue(itemDTO.getUnitValue());
            return purchaseItem;

        }).collect(Collectors.toSet());

        purchaseOrder.setProducts(purchaseItems);

        double totalValue = purchaseItems.stream()
                .mapToDouble(PurchaseItem::calcSubtotal)
                .sum();
        purchaseOrder.setTotalValue(totalValue);

        return purchaseOrder;
    }

    public PurchaseOrderDTO convertToDTO(PurchaseOrder purchaseOrder){
        PurchaseOrderDTO purchaseOrderDTO = new PurchaseOrderDTO();
        purchaseOrderDTO.setId(purchaseOrder.getId());
        purchaseOrderDTO.setSupplierId(purchaseOrder.getSupplier().getId());
        purchaseOrderDTO.setTotalValue(purchaseOrder.getTotalValue());
        purchaseOrderDTO.setProducts(purchaseOrder.getProducts().stream()
                .map(this::convertToPurchaseOrderItemDTO)
                .collect(Collectors.toList()));
        purchaseOrderDTO.setStatus(purchaseOrder.getStatus());
        purchaseOrderDTO.setTotalValue(purchaseOrder.getTotalValue());
        return purchaseOrderDTO;
    }

    public PurchaseItemDTO convertToPurchaseOrderItemDTO(PurchaseItem purchaseItem){
        PurchaseItemDTO purchaseItemDTO = new PurchaseItemDTO();
        purchaseItemDTO.setProductId(purchaseItem.getId().getProduct().getId());
        purchaseItemDTO.setAmount(purchaseItem.getAmount());
        purchaseItemDTO.setUnitValue(purchaseItem.getUnitValue());
        return purchaseItemDTO;
    }
}
