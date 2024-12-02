package com.rr.store.controller;

import com.rr.store.model.Cart;
import com.rr.store.model.CartItem;
import com.rr.store.model.Product;
import com.rr.store.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final ProductRepository productRepository;

    public CartController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public ResponseEntity<Cart> getCart(HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
            System.out.println("New cart initialized and added to session.");
        } else {
            System.out.println("Cart retrieved from session: " + cart);
        }
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/{productId}")
    public ResponseEntity<Cart> addItemToCart(
            @PathVariable Long productId,
            @RequestParam int quantity,
            HttpSession session) {

        if (quantity <= 0) {
            return ResponseEntity.badRequest().body(null);
        }

        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
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

        session.setAttribute("cart", cart); // Ensure the updated cart is saved back to the session
        System.out.println("Cart updated: " + cart);
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Cart> removeItemFromCart(
            @PathVariable Long productId,
            HttpSession session) {

        // Retrieve cart from session
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            return ResponseEntity.ok(new Cart()); // Return empty cart if session is empty
        }

        // Remove the item from the cart
        cart.removeItem(productId);

        // Update session
        session.setAttribute("cart", cart);
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Cart> clearCart(HttpSession session) {
        // Clear the cart
        Cart cart = new Cart();
        session.setAttribute("cart", cart);
        return ResponseEntity.ok(cart);
    }
}
