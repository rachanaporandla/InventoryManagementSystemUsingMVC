package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.dto.Orderdto;
import com.example.InventoryManagementSystem.dto.Productdto;
import com.example.InventoryManagementSystem.entity.Order;
import com.example.InventoryManagementSystem.entity.Product;
import com.example.InventoryManagementSystem.exception.BadRequestException;
import com.example.InventoryManagementSystem.exception.ResourceNotFoundException;
import com.example.InventoryManagementSystem.repository.OrderRepo;
import com.example.InventoryManagementSystem.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private ProductRepo productRepo;

    public Orderdto saveOrder(Orderdto orderDto) {
        if (orderDto.getCustomerName() == null || orderDto.getCustomerName().isEmpty()) {
            throw new BadRequestException("Customer name is required");
        }
        Order order = new Order();
        order.setCustomerName(orderDto.getCustomerName());
        order.setStatus(orderDto.getStatus());
        order.setOrderDate(orderDto.getOrderDate());
        order.setProductQuantities(orderDto.getProductQuantities() != null ? orderDto.getProductQuantities() : new HashMap<>());

        if (orderDto.getProducts() != null && !orderDto.getProducts().isEmpty()) {
            List<Long> productIds = orderDto.getProducts().stream().map(Productdto::getId).toList();
            List<Product> products = productRepo.findAllById(productIds);
            order.setProducts(products);
        }

        Order savedOrder = orderRepo.save(order);
        return mapToOrderDto(savedOrder);
    }
    public List<Orderdto> getAllOrders() {
        return orderRepo.findAll().stream().map(this::mapToOrderDto).toList();
    }
    public Orderdto getOrderById(Long id) {
        Order order = orderRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        return mapToOrderDto(order);
    }
    public Orderdto updateOrder(Long id, Orderdto orderDto) {
        Order existingOrder = orderRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        if (orderDto.getCustomerName() != null)
            existingOrder.setCustomerName(orderDto.getCustomerName());
        if (orderDto.getStatus() != null)
            existingOrder.setStatus(orderDto.getStatus());

        if (orderDto.getProducts() != null && !orderDto.getProducts().isEmpty()) {
            List<Long> productIds = orderDto.getProducts().stream().map(Productdto::getId).toList();
            List<Product> products = productRepo.findAllById(productIds);
            existingOrder.setProducts(products);
        }

        if (orderDto.getProductQuantities() != null && !orderDto.getProductQuantities().isEmpty()) {
            Map<Long, Integer> updatedQuantities = new HashMap<>(existingOrder.getProductQuantities());
            updatedQuantities.putAll(orderDto.getProductQuantities());
            existingOrder.setProductQuantities(updatedQuantities);
        }
        Order updatedOrder = orderRepo.save(existingOrder);
        return mapToOrderDto(updatedOrder);
    }
    public void deleteOrder(Long id) {
        Order order = orderRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        orderRepo.delete(order);
    }
    private Orderdto mapToOrderDto(Order order) {
        List<Productdto> productDtos = order.getProducts() != null ? order.getProducts().stream().map(product -> new Productdto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getQuantity(),
                product.getCategory(),
                product.getSupplier() != null ? product.getSupplier().getId() : null,
                product.getSupplier() != null ? product.getSupplier().getName() : null
        )).toList() : Collections.emptyList();

        return new Orderdto(
                order.getId(),
                order.getCustomerName(),
                order.getOrderDate(),
                order.getStatus(),
                productDtos,
                order.getProductQuantities()
        );
    }
}
