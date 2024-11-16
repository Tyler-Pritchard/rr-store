package com.rr.store.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder // Provides a builder pattern for creating objects
@Table(name = "products") // Explicit table name
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false, length = 100)
    private String category;

    @Column(name = "image_url", length = 1000)
    private String imageUrl;

    // Default Constructor for JPA
    public Product() {
    }

    // All-Args Constructor (for @Builder)
    public Product(Long id, String name, String description, Double price, Integer stock, String category, String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.category = category;
        this.imageUrl = imageUrl;
    }
}
