package com.rr.store.domain.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a product available in the store.
 * 
 * Each product includes details such as name, description, price, stock,
 * category, and an optional image URL. This entity is persisted in the database.
 */
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

    /**
     * Default constructor required by JPA.
     */
    public Product() {
    }

    /**
     * All-args constructor for the @Builder annotation.
     * 
     * @param id          the unique identifier of the product
     * @param name        the name of the product
     * @param description a description of the product
     * @param price       the price of the product
     * @param stock       the stock count of the product
     * @param category    the category of the product
     * @param imageUrl    the image URL of the product
     */
    public Product(Long id, String name, String description, Double price, Integer stock, String category, String imageUrl) {
        validateInputs(name, price, stock, category);
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.category = category;
        this.imageUrl = imageUrl;
    }

    /**
     * Validates the inputs for the product fields.
     * 
     * @param name     the name of the product
     * @param price    the price of the product
     * @param stock    the stock count of the product
     * @param category the category of the product
     * @throws IllegalArgumentException if any input is invalid
     */
    private void validateInputs(String name, Double price, Integer stock, String category) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Product name cannot be null or blank.");
        }
        if (price == null || price <= 0) {
            throw new IllegalArgumentException("Product price must be greater than zero.");
        }
        if (stock == null || stock < 0) {
            throw new IllegalArgumentException("Product stock cannot be negative.");
        }
        if (category == null || category.isBlank()) {
            throw new IllegalArgumentException("Product category cannot be null or blank.");
        }
    }

    /**
     * Sets the name of the product with validation.
     * 
     * @param name the name to set
     */
    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Product name cannot be null or blank.");
        }
        this.name = name;
    }

    /**
     * Sets the price of the product with validation.
     * 
     * @param price the price to set
     */
    public void setPrice(Double price) {
        if (price == null || price <= 0) {
            throw new IllegalArgumentException("Product price must be greater than zero.");
        }
        this.price = price;
    }

    /**
     * Sets the stock of the product with validation.
     * 
     * @param stock the stock to set
     */
    public void setStock(Integer stock) {
        if (stock == null || stock < 0) {
            throw new IllegalArgumentException("Product stock cannot be negative.");
        }
        this.stock = stock;
    }

    /**
     * Sets the category of the product with validation.
     * 
     * @param category the category to set
     */
    public void setCategory(String category) {
        if (category == null || category.isBlank()) {
            throw new IllegalArgumentException("Product category cannot be null or blank.");
        }
        this.category = category;
    }

    /**
     * Provides a string representation of the product for debugging purposes.
     * 
     * @return a string representation of the product
     */
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", category='" + category + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
