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
     * Validates the wiring of the CartRepository.
     */
    @Test
    public void testCartRepositoryLoads() {
        assertThat(cartRepository).isNotNull();
    }

    /**
     * Validates saving and retrieving a Cart entity.
     */
    @Test
    public void testSaveAndRetrieveCart() {
        // Create a new Cart instance
        Cart cart = new Cart();
        
        // Save the cart entity
        Cart savedCart = cartRepository.save(cart);
        
        // Assert the ID is generated and the entity can be retrieved
        assertThat(savedCart.getId()).isNotNull();
        assertThat(cartRepository.findById(savedCart.getId())).isPresent();
    }
}
