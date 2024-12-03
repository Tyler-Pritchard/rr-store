package com.rr.store.controller;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.rr.store.domain.model.Cart;
import com.rr.store.domain.model.Product;
import com.rr.store.infrastructure.repository.CartRepository;
import com.rr.store.infrastructure.repository.ProductRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Unit tests for the CartController.
 * 
 * Validates the behavior of CartController endpoints, including adding items,
 * retrieving the cart, and handling invalid inputs.
 */
@WebMvcTest(CartController.class)
public class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartRepository cartRepository;

    @MockBean
    private ProductRepository productRepository;

    private Cart cart;
    private Product product;

    /**
     * Sets up mock data for testing.
     */
    @BeforeEach
    public void setup() {
        cart = new Cart();
        product = new Product();
        product.setId(1L);
        product.setName("Sample Product");
        product.setPrice(9.99);
    }

    /**
     * Tests the retrieval of an empty cart.
     * 
     * Ensures that the cart is returned as empty when no items are present.
     */
    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testGetCart() throws Exception {
        when(cartRepository.findById(anyLong())).thenReturn(java.util.Optional.of(cart));

        mockMvc.perform(get("/api/cart")
                .with(csrf())) // Include CSRF token
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.items").isEmpty());
    }

    /**
     * Tests adding a valid item to the cart.
     * 
     * Ensures that the item is added successfully and the endpoint returns OK.
     */
    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testAddItemToCart() throws Exception {
        when(productRepository.findById(1L)).thenReturn(java.util.Optional.of(product));
        when(cartRepository.findById(anyLong())).thenReturn(java.util.Optional.of(cart));

        mockMvc.perform(post("/api/cart/1")
                .with(csrf()) // Include CSRF token
                .param("quantity", "2") // Add quantity parameter
                .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.items").isArray());
    }

    /**
     * Tests adding an item with invalid quantity.
     * 
     * Ensures that a bad request status is returned when the quantity is invalid.
     */
    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testAddItemToCartInvalidQuantity() throws Exception {
        mockMvc.perform(post("/api/cart/1")
                .with(csrf()) // Include CSRF token
                .param("quantity", "-1") // Invalid quantity
                .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isBadRequest());
    }

    /**
     * Tests adding an item with a non-existent product ID.
     * 
     * Ensures that a bad request status is returned when the product ID is invalid.
     */
    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testAddItemToCartNonExistentProduct() throws Exception {
        when(productRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        mockMvc.perform(post("/api/cart/1")
                .with(csrf()) // Include CSRF token
                .param("quantity", "1")
                .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isBadRequest());
    }
}
