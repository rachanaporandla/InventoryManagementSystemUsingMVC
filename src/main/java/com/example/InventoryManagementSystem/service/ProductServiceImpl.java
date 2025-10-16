package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.dto.Productdto;
import com.example.InventoryManagementSystem.entity.Product;
import com.example.InventoryManagementSystem.entity.Supplier;
import com.example.InventoryManagementSystem.exception.BadRequestException;
import com.example.InventoryManagementSystem.exception.ResourceNotFoundException;
import com.example.InventoryManagementSystem.repository.ProductRepo;
import com.example.InventoryManagementSystem.repository.SupplierRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private SupplierRepo supplierRepo;

    @Override
    public Productdto saveProductDto(Productdto productdto) {
        validateProductDto(productdto);
        Product product = new Product();
        product.setName(productdto.getName());
        product.setPrice(productdto.getPrice());
        product.setQuantity(productdto.getQuantity());
        product.setCategory(productdto.getCategory());

        if (productdto.getSupplierId() != null) {
            Supplier supplier = supplierRepo.findById(productdto.getSupplierId()).orElseThrow(() -> new ResourceNotFoundException(
                            "Supplier with id " + productdto.getSupplierId() + " not found"));
            product.setSupplier(supplier);
        }
        Product saved = productRepo.save(product);
        return mapEntityToDto(saved);
    }
    public List<Productdto> getAllProducts() {
        return productRepo.findAll().stream().map(this::mapEntityToDto).toList();
    }

    public Productdto getProductByIdDto(Long id) {
        Product product = productRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));
        return mapEntityToDto(product);
    }

    public Productdto updateProductDto(Long id, Productdto productdto) {
        Product product = productRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));

        if (productdto.getName() != null) product.setName(productdto.getName());
        if (productdto.getPrice() != null) {
            if (productdto.getPrice() < 0) throw new BadRequestException("Product price cannot be negative");
            product.setPrice(productdto.getPrice());
        }
        if (productdto.getQuantity() != null) {
            if (productdto.getQuantity() < 0) throw new BadRequestException("Product quantity cannot be negative");
            product.setQuantity(productdto.getQuantity());
        }
        if (productdto.getCategory() != null) product.setCategory(productdto.getCategory());

        if (productdto.getSupplierId() != null) {
            Supplier supplier = supplierRepo.findById(productdto.getSupplierId()).orElseThrow(() -> new ResourceNotFoundException(
                            "Supplier with id " + productdto.getSupplierId() + " not found"));
            product.setSupplier(supplier);
        }

        Product updated = productRepo.save(product);
        return mapEntityToDto(updated);
    }

    @Override
    public void deleteProduct(Long id) {
        Product existing = productRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));
        productRepo.delete(existing);
    }
    private Productdto mapEntityToDto(Product product) {
        Long supplierId = product.getSupplier() != null ? product.getSupplier().getId() : null;
        String supplierName = product.getSupplier() != null ? product.getSupplier().getName() : null;

        return new Productdto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getQuantity(),
                product.getCategory(),
                supplierId,
                supplierName
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
