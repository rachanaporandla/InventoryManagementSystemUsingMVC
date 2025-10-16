package com.example.InventoryManagementSystem.Controller;

import com.example.InventoryManagementSystem.dto.Productdto;
import com.example.InventoryManagementSystem.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Productdto> saveProduct(@RequestBody Productdto productdto) {
        Productdto savedProduct = productService.saveProductDto(productdto);
        return ResponseEntity.ok(savedProduct);
    }
    @GetMapping
    public ResponseEntity<List<Productdto>> getAllProducts() {
        List<Productdto> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Productdto> getProductById(@PathVariable Long id) {
        Productdto product = productService.getProductByIdDto(id);
        return ResponseEntity.ok(product);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Productdto> updateProduct(@PathVariable Long id, @RequestBody Productdto productdto) {
        Productdto updatedProduct = productService.updateProductDto(id, productdto);
        return ResponseEntity.ok(updatedProduct);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted succesfully!");
    }
}
