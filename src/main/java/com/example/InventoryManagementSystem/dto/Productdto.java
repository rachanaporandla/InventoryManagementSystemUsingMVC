package com.example.InventoryManagementSystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Productdto {
    private Long id;
    private String name;
    private Double price;
    private Integer quantity;
    private String category;
    private Long supplierId;
    private String supplierName;
}
