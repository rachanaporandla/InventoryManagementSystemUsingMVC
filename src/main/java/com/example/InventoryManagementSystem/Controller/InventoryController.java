package com.example.InventoryManagementSystem.Controller;

import com.example.InventoryManagementSystem.entity.Product;
import com.example.InventoryManagementSystem.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private ProductService productService;
    @GetMapping
    public ResponseEntity<List<Product>> getInventory() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }
}