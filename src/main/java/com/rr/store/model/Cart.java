package com.rr.store.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cart")
public class Cart implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_id") // Ensures proper linkage between Cart and CartItem
    private List<CartItem> items = new ArrayList<>();

    public Cart() {
        // Default constructor
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public void addItem(CartItem item) {
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

    public void removeItem(Long productId) {
        this.items.removeIf(item -> item.getProduct().getId().equals(productId));
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", items=" + items +
                '}';
    }
}
