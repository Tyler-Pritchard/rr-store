package com.rr.store.controller;

import com.rr.store.domain.model.Cart;
import com.rr.store.domain.service.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing the shopping cart.
 * 
 * Handles HTTP requests related to cart operations and delegates
 * business logic to the CartService.
 */
@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    /**
     * Constructor to inject dependencies.
     * 
     * @param cartService the service layer for cart-related operations
     */
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    /**
     * Retrieves the current user's cart from the session.
     * Initializes a new cart if none exists.
     * 
     * @param session the HTTP session
     * @return the current cart
     */
    @GetMapping
    public ResponseEntity<Cart> getCart(HttpSession session) {
        Cart cart = cartService.getCart(session);
        return ResponseEntity.ok(cart);
    }

    /**
     * Adds an item to the cart.
     * 
     * @param productId the ID of the product to add
     * @param quantity the quantity of the product to add
     * @param session the HTTP session
     * @return the updated cart
     */
    @PostMapping("/{productId}")
    public ResponseEntity<Cart> addItemToCart(
            @PathVariable Long productId,
            @RequestParam int quantity,
            HttpSession session) {

        if (quantity <= 0) {
            return ResponseEntity.badRequest().build();
        }

        Cart cart = cartService.addItemToCart(session, productId, quantity);
        return ResponseEntity.ok(cart);
    }

    /**
     * Removes an item from the cart.
     * 
     * @param productId the ID of the product to remove
     * @param session the HTTP session
     * @return the updated cart
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<Cart> removeItemFromCart(
            @PathVariable Long productId,
            HttpSession session) {

        Cart cart = cartService.removeItemFromCart(session, productId);
        return ResponseEntity.ok(cart);
    }

    /**
     * Clears all items from the cart.
     * 
     * @param session the HTTP session
     * @return an empty cart
     */
    @DeleteMapping("/clear")
    public ResponseEntity<Cart> clearCart(HttpSession session) {
        Cart cart = cartService.clearCart(session);
        return ResponseEntity.ok(cart);
    }

}
