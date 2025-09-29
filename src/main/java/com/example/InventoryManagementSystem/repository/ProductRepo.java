package com.example.InventoryManagementSystem.repository;

import com.example.InventoryManagementSystem.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository <Product, Long> {
}
