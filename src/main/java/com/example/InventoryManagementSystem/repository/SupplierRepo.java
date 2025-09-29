package com.example.InventoryManagementSystem.repository;

import com.example.InventoryManagementSystem.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepo extends JpaRepository<Supplier, Long> {
}

