package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.dto.Productdto;

import java.util.List;

public interface ProductService {
    Productdto saveProductDto(Productdto productdto);
    List<Productdto> getAllProducts();
    Productdto getProductByIdDto(Long id);
    Productdto updateProductDto(Long id, Productdto productdto);
    void deleteProduct(Long id);
}
