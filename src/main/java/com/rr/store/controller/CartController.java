package com.rr.store.controller;

import com.rr.store.model.Cart;
import com.rr.store.model.CartItem;
import com.rr.store.repository.CartRepository;
import com.rr.store.repository.ProductRepository;
import com.rr.store.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    @Autowired
    public CartController(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    // Fetch the current cart
    @GetMapping
    public ResponseEntity<Cart> getCart() {
        Cart cart = cartRepository.findById(1L) // Temporary cart ID for simplicity
            .orElse(new Cart());
        return ResponseEntity.ok(cart);
    }

    // Add an item to the cart
    @PostMapping("/{productId}")
    public ResponseEntity<Cart> addItemToCart(@PathVariable Long productId, @RequestParam int quantity) {
        Cart cart = cartRepository.findById(1L).orElse(new Cart());
        productRepository.findById(productId).ifPresent(product -> {
            CartItem cartItem = new CartItem(product, quantity);
            cart.addItem(cartItem);
        });
        cartRepository.save(cart);
        return ResponseEntity.ok(cart);
    }

    // Remove an item from the cart
    @DeleteMapping("/{productId}")
    public ResponseEntity<Cart> removeItemFromCart(@PathVariable Long productId) {
        Cart cart = cartRepository.findById(1L).orElse(new Cart());
        cart.removeItem(productId);
        cartRepository.save(cart);
        return ResponseEntity.ok(cart);
    }

    // Clear the cart
    @DeleteMapping
    public ResponseEntity<Void> clearCart() {
        cartRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
