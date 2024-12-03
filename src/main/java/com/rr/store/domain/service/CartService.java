package com.rr.store.domain.service;

import com.rr.store.domain.model.Cart;
import com.rr.store.domain.model.CartItem;
import com.rr.store.domain.model.Product;
import com.rr.store.infrastructure.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service layer for managing shopping cart operations.
 * 
 * Encapsulates business logic for cart management, including
 * initialization, adding items, removing items, and clearing the cart.
 */
@Service
public class CartService {

    private final ProductRepository productRepository;

    /**
     * Constructor to inject dependencies.
     * 
     * @param productRepository the repository for accessing product data
     */
    public CartService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Retrieves the current user's cart from the session.
     * Initializes a new cart if none exists.
     * 
     * @param session the HTTP session
     * @return the current cart
     */
    public Cart getCart(HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    /**
     * Adds an item to the cart.
     * 
     * @param session the HTTP session
     * @param productId the ID of the product to add
     * @param quantity the quantity of the product to add
     * @return the updated cart
     * @throws IllegalArgumentException if the product ID is invalid
     */
    public Cart addItemToCart(HttpSession session, Long productId, int quantity) {
        Cart cart = getCart(session);

        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()) {
            throw new IllegalArgumentException("Product not found with ID: " + productId);
        }

        Product product = optionalProduct.get();
        boolean itemExists = false;

        for (CartItem item : cart.getItems()) {
            if (item.getProduct().getId().equals(productId)) {
                item.setQuantity(item.getQuantity() + quantity);
                itemExists = true;
                break;
            }
        }

        if (!itemExists) {
            CartItem cartItem = new CartItem(product, quantity);
            cart.addItem(cartItem);
        }

        session.setAttribute("cart", cart);
        return cart;
    }

    /**
     * Removes an item from the cart.
     * 
     * @param session the HTTP session
     * @param productId the ID of the product to remove
     * @return the updated cart
     */
    public Cart removeItemFromCart(HttpSession session, Long productId) {
        Cart cart = getCart(session);
        cart.removeItem(productId);
        session.setAttribute("cart", cart);
        return cart;
    }

    /**
     * Clears all items from the cart.
     * 
     * @param session the HTTP session
     * @return an empty cart
     */
    public Cart clearCart(HttpSession session) {
        Cart cart = new Cart();
        session.setAttribute("cart", cart);
        return cart;
    }
}
