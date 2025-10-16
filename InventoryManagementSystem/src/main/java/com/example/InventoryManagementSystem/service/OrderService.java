package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.dto.Orderdto;
import java.util.List;

public interface OrderService {
    Orderdto saveOrder(Orderdto orderDto);
    List<Orderdto> getAllOrders();
    Orderdto getOrderById(Long id);
    Orderdto updateOrder(Long id, Orderdto orderDto);
    void deleteOrder(Long id);
}
