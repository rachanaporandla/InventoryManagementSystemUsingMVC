package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.dto.Supplierdto;
import java.util.List;

public interface SupplierService {
    Supplierdto saveSupplier(Supplierdto supplierDto);
    List<Supplierdto> getAllSuppliers();
    Supplierdto getSupplierById(Long id);
    Supplierdto updateSupplier(Long id, Supplierdto supplierDto);
    void deleteSupplier(Long id);
}
