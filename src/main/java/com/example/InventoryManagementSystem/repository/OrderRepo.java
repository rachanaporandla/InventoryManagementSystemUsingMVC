package com.example.InventoryManagementSystem.repository;

import com.example.InventoryManagementSystem.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO orders (customer_name, order_date, status) VALUES (?1, ?2, ?3)", nativeQuery = true)
    void insertOrder(String customerName, java.sql.Date orderDate, String status);

    // 🟢 READ - Get all orders
    @Query(value = "SELECT * FROM orders", nativeQuery = true)
    List<Order> getAllOrders();

    // 🟢 READ - Get order by ID
    @Query(value = "SELECT * FROM orders WHERE id = ?1", nativeQuery = true)
    Order getOrderById(Long id);

    // 🟢 UPDATE order
    @Modifying
    @Transactional
    @Query(value = "UPDATE orders SET customer_name = ?2, order_date = ?3, status = ?4 WHERE id = ?1", nativeQuery = true)
    void updateOrder(Long id, String customerName, java.sql.Date orderDate, String status);

    // 🟢 DELETE order
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM orders WHERE id = ?1", nativeQuery = true)
    void deleteOrder(Long id);

    // --- Optional queries ---
    @Query(value = "SELECT * FROM orders WHERE status = ?1", nativeQuery = true)
    List<Order> findOrdersByStatus(String status);

    @Query(value = "SELECT * FROM orders WHERE customer_name = ?1", nativeQuery = true)
    List<Order> findOrdersByCustomerName(String customerName);
}
