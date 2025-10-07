package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.dto.Supplierdto;
import com.example.InventoryManagementSystem.entity.Supplier;
import com.example.InventoryManagementSystem.exception.BadRequestException;
import com.example.InventoryManagementSystem.exception.ResourceNotFoundException;
import com.example.InventoryManagementSystem.repository.SupplierRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierRepo supplierRepo;

    // --- DTO-based methods for controllers ---
    @Override
    public Supplierdto saveSupplier(Supplierdto supplierDto) {
        validateSupplierDto(supplierDto);
        Supplier supplier = new Supplier();
        if (supplierDto.getName() != null) supplier.setName(supplierDto.getName());
        if (supplierDto.getContact() != null) supplier.setContact(supplierDto.getContact());
        if (supplierDto.getEmail() != null) supplier.setEmail(supplierDto.getEmail());
        Supplier saved = supplierRepo.save(supplier);
        return mapToDto(saved);
    }

    @Override
    public List<Supplierdto> getAllSuppliers() {
        return supplierRepo.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public Supplierdto getSupplierById(Long id) {
        Supplier supplier = supplierRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id " + id));
        return mapToDto(supplier);
    }

    @Override
    public Supplierdto updateSupplier(Long id, Supplierdto supplierDto) {
        Supplier existing = supplierRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id " + id));

        if (supplierDto.getName() != null) existing.setName(supplierDto.getName());
        if (supplierDto.getContact() != null) existing.setContact(supplierDto.getContact());
        if (supplierDto.getEmail() != null) existing.setEmail(supplierDto.getEmail());

        Supplier updated = supplierRepo.save(existing);
        return mapToDto(updated);
    }

    @Override
    public void deleteSupplier(Long id) {
        Supplier existing = supplierRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id " + id));
        supplierRepo.delete(existing);
    }

    // --- Entity-based method for internal use (Product mapping) ---

    public Supplier getSupplierEntityById(Long id) {
        return supplierRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id " + id));
    }

    // --- Helper method to convert entity -> DTO ---
    private Supplierdto mapToDto(Supplier supplier) {
        return new Supplierdto(
                supplier.getId(),
                supplier.getName(),
                supplier.getContact(),
                supplier.getEmail()
        );
    }

    // --- Validation ---
    private void validateSupplierDto(Supplierdto dto) {
        if (dto.getName() == null || dto.getName().isBlank())
            throw new BadRequestException("Supplier name is required");
        if (dto.getEmail() != null && !dto.getEmail().contains("@"))
            throw new BadRequestException("Invalid email format");
    }
}
