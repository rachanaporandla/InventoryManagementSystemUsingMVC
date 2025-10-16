package com.example.InventoryManagementSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Orderdto {
    private Long id;
    private String customerName;
    private LocalDate orderDate;
    private String status;
    private List<Productdto> products;
    private Map<Long, Integer> productQuantities;
}
