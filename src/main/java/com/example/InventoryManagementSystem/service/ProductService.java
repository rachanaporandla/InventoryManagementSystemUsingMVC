package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.dto.Productdto;

import java.util.List;

public interface ProductService {
    Productdto saveProductDto(Productdto productdto);
    List<Productdto> getAllProducts();
    Productdto getProductByIdDto(Long id);
    Productdto updateProductDto(Long id, Productdto productdto);
    Productdto updateStockDto(Long id, Integer quantity);
    void deleteProduct(Long id);
    List<Productdto> getProductsByIds(List<Long> ids);
}
