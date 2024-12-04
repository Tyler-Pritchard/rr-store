package com.rr.store.controller;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.rr.store.domain.model.Cart;
import com.rr.store.domain.model.Product;
import com.rr.store.domain.service.CartService;
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

    @MockBean
    private CartService cartService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartRepository cartRepository;

    @MockBean
    private ProductRepository productRepository;

    private Cart cart;
    private Product product;

    @BeforeEach
    public void setup() {
        cart = new Cart();
        product = new Product();
        product.setId(1L);
        product.setName("Sample Product");
        product.setPrice(9.99);
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testGetCart() throws Exception {
        when(cartRepository.findById(anyLong())).thenReturn(java.util.Optional.of(cart));

        mockMvc.perform(get("/api/cart")
                .with(csrf())) // Include CSRF token
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.items").isEmpty());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testAddItemToCart() throws Exception {
        when(productRepository.findById(1L)).thenReturn(java.util.Optional.of(product));
        when(cartRepository.findById(anyLong())).thenReturn(java.util.Optional.of(cart));

        mockMvc.perform(post("/api/cart/1")
                .with(csrf())
                .param("quantity", "2")
                .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.items").isArray());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testAddItemToCartInvalidQuantity() throws Exception {
        mockMvc.perform(post("/api/cart/1")
                .with(csrf())
                .param("quantity", "-1")
                .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testAddItemToCartNonExistentProduct() throws Exception {
        when(productRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        mockMvc.perform(post("/api/cart/1")
                .with(csrf())
                .param("quantity", "1")
                .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isBadRequest());
    }
}
