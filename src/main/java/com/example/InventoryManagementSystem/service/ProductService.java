package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.entity.Product;
import java.util.List;

public interface ProductService {
    Product saveProduct(Product product);
    List<Product> getAllProducts();
    Product getProductById(Long id);
    Product updateProduct(Long id, Product productDetails);
    void deleteProduct(Long id);
    Product updateStock(Long id, Integer quantity);
}
