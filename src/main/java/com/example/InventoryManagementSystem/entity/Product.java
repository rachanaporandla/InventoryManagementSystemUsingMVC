package com.example.InventoryManagementSystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double price;
    private Integer quantity;
    private String category;

    @ManyToOne
    @JoinColumn(name = "supplier_id")

    private Supplier supplier;

    @ManyToMany(mappedBy = "products")
    @JsonIgnore

    private List<Order> orders = new ArrayList<>();
}
