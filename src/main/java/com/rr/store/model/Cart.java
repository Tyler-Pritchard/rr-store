package com.rr.store.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();

    public void addItem(CartItem cartItem) {
        this.items.add(cartItem);
    }

    public void removeItem(Long productId) {
        this.items.removeIf(item -> item.getProduct().getId().equals(productId));
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void clearCart() {
        this.items.clear();
    }
}
