package com.rr.store.controller;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.rr.store.model.Cart;
import com.rr.store.model.Product;
import com.rr.store.repository.CartRepository;
import com.rr.store.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

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
                .with(csrf())) // Include CSRF token if required
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.items").isEmpty());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testAddItemToCart() throws Exception {
        when(productRepository.findById(1L)).thenReturn(java.util.Optional.of(product));
        when(cartRepository.findById(anyLong())).thenReturn(java.util.Optional.of(cart));

        mockMvc.perform(post("/api/cart/1")
                .with(csrf()) // Include CSRF token if required
                .param("quantity", "2") // Add quantity parameter
                .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());
    }
}
