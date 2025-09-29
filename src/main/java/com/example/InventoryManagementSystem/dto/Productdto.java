package com.example.InventoryManagementSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Productdto {
    private String name;
    private double price;
    private Integer quantity;
    private String category;
    private Long supplierId;
}
