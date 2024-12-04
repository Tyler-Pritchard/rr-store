package com.rr.store.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.rr.store.domain.model.Cart;

/**
 * Repository interface for managing Cart entities.
 * 
 * Provides CRUD operations and allows for custom queries
 * to interact with the Cart database table.
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    /**
     * Finds a cart by its ID, along with all associated items.
     * 
     * @param id the ID of the cart to find
     * @return the cart with the given ID, or null if not found
     */
    default Cart findByIdWithItems(Long id) {
        throw new UnsupportedOperationException("This feature is not yet implemented.");
    }    
}
