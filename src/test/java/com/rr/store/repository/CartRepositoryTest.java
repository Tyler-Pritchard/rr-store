package com.rr.store.repository;

import com.rr.store.domain.model.Cart;
import com.rr.store.infrastructure.repository.CartRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for the CartRepository.
 * 
 * Validates repository methods for managing Cart entities,
 * ensuring correct database interactions.
 */
@DataJpaTest
public class CartRepositoryTest {

    @Autowired
    private CartRepository cartRepository;

    /**
     * Placeholder test to validate repository wiring.
     * 
     * Ensures that the CartRepository is correctly injected
     * and can save entities.
     */
    @Test
    public void testCartRepositoryLoads() {
        assertThat(cartRepository).isNotNull();
    }

    /**
     * Example test case for saving and retrieving a cart.
     * 
     * This can be expanded in the future to include more complex scenarios.
     */
    @Test
    public void testSaveAndRetrieveCart() {
        // Create a new Cart instance
        Cart cart = new Cart();
        Cart savedCart = cartRepository.save(cart);

        // Assert that the saved cart can be retrieved by ID
        assertThat(savedCart.getId()).isNotNull();
        assertThat(cartRepository.findById(savedCart.getId())).isPresent();
    }
}
