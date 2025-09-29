package com.example.InventoryManagementSystem.service;

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

    public Product saveProduct(Product product) {
        if (product.getName() == null || product.getName().isEmpty()) {
            throw new BadRequestException("Product name is required");
        }
        if (product.getPrice() < 0) {
            throw new BadRequestException("Product price cannot be negative");
        }
        if (product.getSupplier() != null && product.getSupplier().getId() != null) {
            Supplier supplier = supplierRepo.findById(product.getSupplier().getId()).orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id: " + product.getSupplier().getId()));
            product.setSupplier(supplier);
        }
        return productRepo.save(product);
    }
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }
    public Product getProductById(Long id) {
        return productRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }
    public Product updateProduct(Long id, Product productDetails) {
        Product product = productRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        if (productDetails.getName() != null && !productDetails.getName().isEmpty()) {
            product.setName(productDetails.getName());
        }
        if (productDetails.getPrice() < 0) {
            throw new BadRequestException("Product price cannot be negative");
        }
        if (productDetails.getPrice() > 0) {
            product.setPrice(productDetails.getPrice());
        }
        if (productDetails.getQuantity() != null && productDetails.getQuantity() < 0) {
            throw new BadRequestException("Product quantity cannot be negative");
        }
        if (productDetails.getQuantity() != null) {
            product.setQuantity(productDetails.getQuantity());
        }
        if (productDetails.getCategory() != null) {
            product.setCategory(productDetails.getCategory());
        }
        if (productDetails.getSupplier() != null && productDetails.getSupplier().getId() != null) {
            Supplier supplier = supplierRepo.findById(productDetails.getSupplier().getId()).orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id: " + productDetails.getSupplier().getId()));
            product.setSupplier(supplier);
        }
        return productRepo.save(product);
    }
    public void deleteProduct(Long id) {
        Product product = productRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        productRepo.delete(product);
    }
    public Product updateStock(Long id, Integer quantity) {
        if (quantity == null || quantity < 0) {
            throw new BadRequestException("Stock quantity must be zero or positive");
        }
        Product product = productRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        product.setQuantity(quantity);
        return productRepo.save(product);
    }
}
