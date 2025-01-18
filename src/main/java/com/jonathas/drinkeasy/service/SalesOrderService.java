package com.jonathas.drinkeasy.service;

import com.jonathas.drinkeasy.exceptions.SalesOrderNotFoundException;
import com.jonathas.drinkeasy.model.dto.OrderItemDTO;
import com.jonathas.drinkeasy.model.dto.SalesOrderDTO;
import com.jonathas.drinkeasy.model.entity.*;
import com.jonathas.drinkeasy.repository.ClientRepository;
import com.jonathas.drinkeasy.repository.OrderItemRepository;
import com.jonathas.drinkeasy.repository.ProductRepository;
import com.jonathas.drinkeasy.repository.SalesOrderRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SalesOrderService {
    @Autowired
    private SalesOrderRepository salesOrderRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    public List<SalesOrderDTO> findAll() {
        List<SalesOrder> salesOrders = salesOrderRepository.findAll();

        return salesOrders.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<SalesOrderDTO> findById(@NotNull UUID id){
        if(id == null){
            throw new IllegalArgumentException("The given id must not be null");
        }

        Optional<SalesOrder> salesOrder = salesOrderRepository.findById(id);
        if(!salesOrder.isPresent()){
            throw new SalesOrderNotFoundException("Sales Order not found!");
        }
        return Optional.of(convertToDTO(salesOrder.get()));
    }

    @Transactional
    public SalesOrderDTO update(UUID id, SalesOrderDTO salesOrderDTO){
        Optional<SalesOrder> optionalSalesOrder = salesOrderRepository.findById(id);

        if(!optionalSalesOrder.isPresent()){
            throw new SalesOrderNotFoundException("Sales Order not found");
        }
        SalesOrder existingSalesOrder = optionalSalesOrder.get();


        existingSalesOrder.setDate(salesOrderDTO.getDate());
        existingSalesOrder.setStatus(salesOrderDTO.getStatus());


        Client client = clientRepository.findById(salesOrderDTO.getClientId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado."));
        existingSalesOrder.setClient(client);


        Set<OrderItem> updatedItems = salesOrderDTO.getProducts().stream().map(itemDTO -> {
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado."));

            OrderItem orderItem = new OrderItem();
            OrderItemPk orderItemPk = new OrderItemPk();
            orderItemPk.setSalesOrder(existingSalesOrder);
            orderItemPk.setProduct(product);

            orderItem.setId(orderItemPk);
            orderItem.setAmount(itemDTO.getAmount());
            orderItem.setUnitValue(itemDTO.getUnitValue());

            return orderItem;
        }).collect(Collectors.toSet());

        existingSalesOrder.setProducts(updatedItems);


        double totalValue = updatedItems.stream()
                .mapToDouble(OrderItem::calcSubtotal)
                .sum();
        existingSalesOrder.setTotalValue(totalValue);

        SalesOrder updatedSalesOrder = salesOrderRepository.save(existingSalesOrder);

        return convertToDTO(updatedSalesOrder);
    }

    @Transactional
    public void delete(UUID id){
        Optional<SalesOrder> salesOrder=  salesOrderRepository.findById(id);

        if(!salesOrder.isPresent()){
            throw new SalesOrderNotFoundException("Sales Order not found");
        }
        salesOrderRepository.delete(salesOrder.get());
    }

    @Transactional
    public SalesOrderDTO save(SalesOrderDTO salesOrderDTO){
        SalesOrder salesOrder = create(salesOrderDTO);
        SalesOrder savedSalesOrder = salesOrderRepository.save(salesOrder);

        return convertToDTO(savedSalesOrder);
    }

    public SalesOrder create(SalesOrderDTO salesOrderDTO) {
        SalesOrder salesOrder = new SalesOrder();
        salesOrder.setDate(salesOrderDTO.getDate());
        salesOrder.setStatus(salesOrderDTO.getStatus());


        Client client = clientRepository.findById(salesOrderDTO.getClientId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado."));
        salesOrder.setClient(client);


        Set<OrderItem> orderItems = salesOrderDTO.getProducts().stream().map(itemDTO -> {
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado."));

            OrderItem orderItem = new OrderItem();
            OrderItemPk orderItemPk = new OrderItemPk();
            orderItemPk.setSalesOrder(salesOrder);
            orderItemPk.setProduct(product);

            orderItem.setId(orderItemPk);
            orderItem.setAmount(itemDTO.getAmount());
            orderItem.setUnitValue(itemDTO.getUnitValue());

            return orderItem;
        }).collect(Collectors.toSet());

        salesOrder.setProducts(orderItems);


        double totalValue = orderItems.stream()
                .mapToDouble(OrderItem::calcSubtotal)
                .sum();
        salesOrder.setTotalValue(totalValue);

        return salesOrder;
    }

    public SalesOrderDTO convertToDTO(SalesOrder salesOrder){
        SalesOrderDTO salesOrderDTO = new SalesOrderDTO();
        salesOrderDTO.setId(salesOrder.getId());
        salesOrderDTO.setClientId(salesOrder.getClient().getId());
        salesOrderDTO.setDate(salesOrder.getDate());
        salesOrderDTO.setProducts(salesOrder.getProducts().stream()
                .map(this::convertToOrderItemDTO)
                .collect(Collectors.toList()));
        salesOrderDTO.setStatus(salesOrder.getStatus());
        salesOrderDTO.setTotalValue(salesOrder.getTotalValue());
        return salesOrderDTO;
    }

    public OrderItemDTO convertToOrderItemDTO(OrderItem orderItem) {
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setProductId(orderItem.getId().getProduct().getId());
        orderItemDTO.setAmount(orderItem.getAmount());
        orderItemDTO.setUnitValue(orderItem.getUnitValue());
        return orderItemDTO;
    }
}
