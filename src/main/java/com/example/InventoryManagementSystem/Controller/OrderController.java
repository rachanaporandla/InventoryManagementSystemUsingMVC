package com.example.InventoryManagementSystem.Controller;

import com.example.InventoryManagementSystem.dto.Orderdto;
import com.example.InventoryManagementSystem.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<List<Orderdto>> getAllOrders() {
        List<Orderdto> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Orderdto> getOrderById(@PathVariable Long id) {
        Orderdto order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }
    @PostMapping
    public ResponseEntity<Orderdto> createOrder(@RequestBody Orderdto orderDto) {
        Orderdto createdOrder = orderService.saveOrder(orderDto);
        return ResponseEntity.ok(createdOrder);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Orderdto> updateOrder(@PathVariable Long id, @RequestBody Orderdto orderDto) {
        Orderdto updatedOrder = orderService.updateOrder(id, orderDto);
        return ResponseEntity.ok(updatedOrder);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok("Order deleted successfully.");
    }
}
