package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.dto.Productdto;
import com.example.InventoryManagementSystem.entity.Product;
import com.example.InventoryManagementSystem.entity.Supplier;
import com.example.InventoryManagementSystem.exception.BadRequestException;
import com.example.InventoryManagementSystem.exception.ResourceNotFoundException;
import com.example.InventoryManagementSystem.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private SupplierService supplierService;

    @Override
    public Productdto saveProductDto(Productdto productdto) {
        validateProductDto(productdto);
        Product product = mapDtoToEntity(productdto);
        Product saved = productRepo.save(product);
        return mapEntityToDto(saved);
    }

    @Override
    public List<Productdto> getAllProducts() {
        return productRepo.findAll().stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Productdto getProductByIdDto(Long id) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));
        return mapEntityToDto(product);
    }

    @Override
    public Productdto updateProductDto(Long id, Productdto productdto) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));

        if (productdto.getName() != null) product.setName(productdto.getName());
        if (productdto.getPrice() != null) {
            if (productdto.getPrice() < 0)
                throw new BadRequestException("Product price cannot be negative");
            product.setPrice(productdto.getPrice());
        }
        if (productdto.getQuantity() != null) {
            if (productdto.getQuantity() < 0)
                throw new BadRequestException("Product quantity cannot be negative");
            product.setQuantity(productdto.getQuantity());
        }
        if (productdto.getCategory() != null) product.setCategory(productdto.getCategory());

        // --- Use entity method to fetch Supplier ---
        if (productdto.getSupplierId() != null) {
            Supplier supplier = supplierService.getSupplierEntityById(productdto.getSupplierId());
            product.setSupplier(supplier);
        }

        Product updated = productRepo.save(product);
        return mapEntityToDto(updated);
    }

    @Override
    public Productdto updateStockDto(Long id, Integer quantity) {
        if (quantity < 0) throw new BadRequestException("Quantity cannot be negative");
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));
        product.setQuantity(quantity);
        return mapEntityToDto(productRepo.save(product));
    }

    @Override
    public void deleteProduct(Long id) {
        Product existing = productRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));
        productRepo.delete(existing);
    }

    @Override
    public List<Productdto> getProductsByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) return List.of();
        List<Product> products = productRepo.findAllById(ids);
        return products.stream().map(this::mapEntityToDto).collect(Collectors.toList());
    }

    // --- Helper methods ---
    private Product mapDtoToEntity(Productdto dto) {
        Product product = new Product();
        if (dto.getName() != null) product.setName(dto.getName());
        if (dto.getPrice() != null) product.setPrice(dto.getPrice());
        if (dto.getQuantity() != null) product.setQuantity(dto.getQuantity());
        if (dto.getCategory() != null) product.setCategory(dto.getCategory());

        if (dto.getSupplierId() != null) {
            // <-- This now returns Supplier entity, not DTO
            Supplier supplier = supplierService.getSupplierEntityById(dto.getSupplierId());
            product.setSupplier(supplier);
        }

        return product;
    }

    private Productdto mapEntityToDto(Product product) {
        Long supplierId = product.getSupplier() != null ? product.getSupplier().getId() : null;
        return new Productdto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getQuantity(),
                product.getCategory(),
                supplierId
        );
    }

    private void validateProductDto(Productdto dto) {
        if (dto.getPrice() != null && dto.getPrice() < 0)
            throw new BadRequestException("Product price cannot be negative");
        if (dto.getQuantity() != null && dto.getQuantity() < 0)
            throw new BadRequestException("Product quantity cannot be negative");
        if (dto.getName() == null || dto.getName().isBlank())
            throw new BadRequestException("Product name is required");
    }
}
