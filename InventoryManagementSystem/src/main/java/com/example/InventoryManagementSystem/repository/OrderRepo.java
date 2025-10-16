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
    @Query(value = "INSERT INTO orders (customer_name, order_date, status) VALUES (?, ?, ?)", nativeQuery = true)
    void insertOrder(String customerName, java.sql.Date orderDate, String status);

    @Query(value = "SELECT * FROM orders", nativeQuery = true)
    List<Order> getAllOrders();

    @Query(value = "SELECT * FROM orders WHERE id = ?", nativeQuery = true)
    Order getOrderById(Long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE orders SET customer_name = ?, order_date = ?, status = ? WHERE id = ?", nativeQuery = true)
    void updateOrder(Long id, String customerName, java.sql.Date orderDate, String status);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM orders WHERE id = ?", nativeQuery = true)
    void deleteOrder(Long id);

}
