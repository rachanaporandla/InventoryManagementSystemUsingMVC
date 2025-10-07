package com.example.InventoryManagementSystem.Controller;

import com.example.InventoryManagementSystem.dto.Supplierdto;
import com.example.InventoryManagementSystem.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @PostMapping
    public ResponseEntity<Supplierdto> addSupplier(@RequestBody Supplierdto supplierDto) {
        Supplierdto saved = supplierService.saveSupplier(supplierDto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<Supplierdto>> getAllSuppliers() {
        List<Supplierdto> suppliers = supplierService.getAllSuppliers();
        return ResponseEntity.ok(suppliers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Supplierdto> getSupplierById(@PathVariable Long id) {
        Supplierdto supplier = supplierService.getSupplierById(id);
        return ResponseEntity.ok(supplier);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Supplierdto> updateSupplier(@PathVariable Long id, @RequestBody Supplierdto supplierDto) {
        Supplierdto updated = supplierService.updateSupplier(id, supplierDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSupplier(@PathVariable Long id) {
        supplierService.deleteSupplier(id);
        return ResponseEntity.ok("Supplier deleted successfully.");
    }
}
