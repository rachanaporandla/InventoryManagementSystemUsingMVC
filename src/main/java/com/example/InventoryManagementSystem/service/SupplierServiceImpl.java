package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.entity.Supplier;
import com.example.InventoryManagementSystem.exception.BadRequestException;
import com.example.InventoryManagementSystem.exception.ResourceNotFoundException;
import com.example.InventoryManagementSystem.repository.SupplierRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierRepo supplierRepo;

    public Supplier saveSupplier(Supplier supplier) {
        if (supplier.getName() == null || supplier.getName().isEmpty()) {
            throw new BadRequestException("Supplier name is required");
        }
        if (supplier.getEmail() == null || supplier.getEmail().isEmpty()) {
            throw new BadRequestException("Supplier email is required");
        }
        return supplierRepo.save(supplier);
    }
    public List<Supplier> getAllSuppliers() {
        return supplierRepo.findAll();
    }
    public Supplier getSupplierById(Long id) {
        return supplierRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id: " + id));
    }
    public Supplier updateSupplier(Long id, Supplier supplierDetails) {
        Supplier supplier = supplierRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id: " + id));
        if (supplierDetails.getName() != null && !supplierDetails.getName().isEmpty()) {
            supplier.setName(supplierDetails.getName());
        }
        if (supplierDetails.getContact() != null) {
            supplier.setContact(supplierDetails.getContact());
        }
        if (supplierDetails.getEmail() != null && !supplierDetails.getEmail().isEmpty()) {
            supplier.setEmail(supplierDetails.getEmail());
        }
        return supplierRepo.save(supplier);
    }
    public void deleteSupplier(Long id) {
        Supplier supplier = supplierRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id: " + id));
        supplierRepo.delete(supplier);
    }
}
