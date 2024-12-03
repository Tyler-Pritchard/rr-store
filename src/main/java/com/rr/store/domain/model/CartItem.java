package com.rr.store.domain.model;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * Represents an item in a shopping cart.
 * 
 * Each cart item is associated with a specific product and tracks the quantity
 * of that product in the cart. This entity is persisted in the database.
 */
@Entity
@Table(name = "cart_items")
public class CartItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false) // Ensures proper foreign key mapping
    private Product product;

    private int quantity;

    /**
     * Default constructor required by JPA.
     */
    public CartItem() {
        // Default constructor
    }

    /**
     * Constructs a CartItem with the specified product and quantity.
     * 
     * @param product the product associated with this cart item
     * @param quantity the quantity of the product
     * @throws IllegalArgumentException if the product is null or the quantity is less than 1
     */
    public CartItem(Product product, int quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null.");
        }
        if (quantity < 1) {
            throw new IllegalArgumentException("Quantity must be at least 1.");
        }
        this.product = product;
        this.quantity = quantity;
    }

    /**
     * Gets the unique identifier for the cart item.
     * 
     * @return the cart item's ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the cart item.
     * 
     * @param id the cart item's ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the product associated with this cart item.
     * 
     * @return the associated product
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Sets the product associated with this cart item.
     * 
     * @param product the product to associate with this cart item
     * @throws IllegalArgumentException if the product is null
     */
    public void setProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null.");
        }
        this.product = product;
    }

    /**
     * Gets the quantity of the product in this cart item.
     * 
     * @return the quantity of the product
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the product in this cart item.
     * 
     * @param quantity the quantity to set
     * @throws IllegalArgumentException if the quantity is less than 1
     */
    public void setQuantity(int quantity) {
        if (quantity < 1) {
            throw new IllegalArgumentException("Quantity must be at least 1.");
        }
        this.quantity = quantity;
    }

    /**
     * Provides a string representation of the cart item for debugging purposes.
     * 
     * @return a string representation of the cart item
     */
    @Override
    public String toString() {
        return "CartItem{" +
                "id=" + id +
                ", product=" + product +
                ", quantity=" + quantity +
                '}';
    }
}
