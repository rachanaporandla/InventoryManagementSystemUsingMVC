package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.entity.Order;
import com.example.InventoryManagementSystem.exception.BadRequestException;
import com.example.InventoryManagementSystem.exception.ResourceNotFoundException;
import com.example.InventoryManagementSystem.repository.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepo orderRepo;

    public Order saveOrder(Order order) {
        if (order.getCustomerName() == null || order.getCustomerName().isEmpty()) {
            throw new BadRequestException("Customer name is required to place an order");
        }
        return orderRepo.save(order);
    }
    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }
    public Order getOrderById(Long id) {
        return orderRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
    }
    public Order updateOrder(Long id, Order orderDetails) {
        Order order = orderRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));

        if (orderDetails.getCustomerName() != null && !orderDetails.getCustomerName().isEmpty()) {
            order.setCustomerName(orderDetails.getCustomerName());
        }
        if (orderDetails.getOrderDate() != null) {
            order.setOrderDate(orderDetails.getOrderDate());
        }
        if (orderDetails.getProducts() != null && !orderDetails.getProducts().isEmpty()) {
            order.setProducts(orderDetails.getProducts());
        }
        if (orderDetails.getProductQuantities() != null && !orderDetails.getProductQuantities().isEmpty()) {
            order.setProductQuantities(orderDetails.getProductQuantities());
        }
        if (orderDetails.getStatus() != null && !orderDetails.getStatus().isEmpty()) {
            order.setStatus(orderDetails.getStatus());
        }
        return orderRepo.save(order);
    }
    public void deleteOrder(Long id) {
        Order order = orderRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        orderRepo.delete(order);
    }
}
