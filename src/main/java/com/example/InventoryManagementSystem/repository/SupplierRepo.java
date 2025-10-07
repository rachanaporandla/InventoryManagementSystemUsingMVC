package com.example.InventoryManagementSystem.repository;

import com.example.InventoryManagementSystem.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
public interface SupplierRepo extends JpaRepository<Supplier, Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO supplier (name, contact, email) VALUES (?, ?, ?)", nativeQuery = true)
    void insertSupplier(String name, String contact, String email);

    @Query(value = "SELECT * FROM supplier", nativeQuery = true)
    List<Supplier> getAllSuppliers();

    @Query(value = "SELECT * FROM supplier WHERE id = ?", nativeQuery = true)
    Supplier getSupplierById(Long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE supplier SET name = ?2, contact = ?3, email = ?4 WHERE id = ?1", nativeQuery = true)
    void updateSupplier(Long id, String name, String contact, String email);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM supplier WHERE id = ?1", nativeQuery = true)
    void deleteSupplier(Long id);
}
