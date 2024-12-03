package com.rr.store.domain.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a shopping cart entity.
 * 
 * A shopping cart contains multiple cart items, each associated with a product.
 * This entity is persisted in the database and includes helper methods for
 * adding and removing items.
 */
@Entity
@Table(name = "cart")
public class Cart implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_id") // Ensures proper linkage between Cart and CartItem
    private List<CartItem> items = new ArrayList<>();

    /**
     * Default constructor required by JPA.
     */
    public Cart() {
        // Default constructor
    }

    /**
     * Gets the unique identifier for the cart.
     * 
     * @return the cart's ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the cart.
     * 
     * @param id the cart's ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets an unmodifiable list of items in the cart.
     * 
     * @return an unmodifiable list of cart items
     */
    public List<CartItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    /**
     * Sets the items for the cart.
     * Replaces all existing items with the provided list.
     * 
     * @param items the list of items to set
     * @throws IllegalArgumentException if the provided list is null
     */
    public void setItems(List<CartItem> items) {
        if (items == null) {
            throw new IllegalArgumentException("Items list cannot be null.");
        }
        this.items.clear();
        this.items.addAll(items);
    }

    /**
     * Adds a new item to the cart or updates the quantity if the item already exists.
     * 
     * @param item the cart item to add
     * @throws IllegalArgumentException if the item is null or has no associated product
     */
    public void addItem(CartItem item) {
        if (item == null || item.getProduct() == null) {
            throw new IllegalArgumentException("Item or its associated product cannot be null.");
        }

        // Check if the item already exists in the cart
        for (CartItem existingItem : items) {
            if (existingItem.getProduct().getId().equals(item.getProduct().getId())) {
                // Update the quantity if the product already exists
                existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
                return;
            }
        }

        // Add the new item to the cart
        this.items.add(item);
    }

    /**
     * Removes an item from the cart based on the product ID.
     * 
     * @param productId the ID of the product to remove
     * @throws IllegalArgumentException if the product ID is null
     */
    public void removeItem(Long productId) {
        if (productId == null) {
            throw new IllegalArgumentException("Product ID cannot be null.");
        }
        this.items.removeIf(item -> item.getProduct().getId().equals(productId));
    }

    /**
     * Provides a string representation of the cart for debugging purposes.
     * 
     * @return a string representation of the cart
     */
    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", items=" + items +
                '}';
    }
}
