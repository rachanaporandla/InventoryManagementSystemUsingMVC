package com.example.InventoryManagementSystem.repository;

import com.example.InventoryManagementSystem.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO product (name, price, quantity, category, supplier_id) VALUES (?1, ?2, ?3, ?4, ?5)", nativeQuery = true)
    void insertProduct(String name, double price, int quantity, String category, Long supplierId);

    @Query(value = "SELECT * FROM product", nativeQuery = true)
    List<Product> getAllProducts();

    @Query("SELECT p FROM Product p WHERE p.id IN :ids")
    List<Product> findProductsByIds(@Param("ids") List<Long> ids);

    @Modifying
    @Transactional
    @Query(value = "UPDATE product SET name = ?2, price = ?3, quantity = ?4, category = ?5, supplier_id = ?6 WHERE id = ?1", nativeQuery = true)
    void updateProduct(Long id, String name, double price, int quantity, String category, Long supplierId);

    // DELETE product
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM product WHERE id = ?1", nativeQuery = true)
    void deleteProduct(Long id);
}
