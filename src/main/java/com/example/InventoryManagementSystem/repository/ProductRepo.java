package com.example.InventoryManagementSystem.repository;

import com.example.InventoryManagementSystem.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO product (name, price, quantity, category, supplier_id) VALUES (?, ?, ?, ?, ?)", nativeQuery = true)
    void insertProduct(String name, double price, int quantity, String category, Long supplierId);

    @Query(value = "SELECT * FROM product", nativeQuery = true)
    List<Product> getAllProducts();

    @Query(value = "SELECT * FROM Product WHERE id = ?", nativeQuery = true)
    Product getProductById(Long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE product SET name = ?, price = ?, quantity = ?, category = ?, supplier_id = ? WHERE id = ?", nativeQuery = true)
    void updateProduct(Long id, String name, double price, int quantity, String category, Long supplierId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM product WHERE id = ?", nativeQuery = true)
    void deleteProduct(Long id);
}
