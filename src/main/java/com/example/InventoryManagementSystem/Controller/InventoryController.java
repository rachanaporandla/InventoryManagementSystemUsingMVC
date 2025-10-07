package com.example.InventoryManagementSystem.Controller;

import com.example.InventoryManagementSystem.dto.Productdto;
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
    public ResponseEntity<List<Productdto>> getInventory() {
        List<Productdto> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }
}
