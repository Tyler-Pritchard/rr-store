package com.rr.store.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.rr.store.domain.model.Product;

import java.util.Optional;

/**
 * Repository interface for managing Product entities.
 * 
 * Provides CRUD operations and custom queries to interact with the Product
 * database table. Uses Spring Data JPA to simplify implementation.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Finds a product by its name.
     * 
     * @param name the name of the product to find
     * @return an Optional containing the product if found, or empty if not
     */
    Optional<Product> findByName(String name);

    /**
     * Checks if a product with the given name exists.
     * 
     * @param name the name of the product to check
     * @return true if a product with the given name exists, false otherwise
     */
    boolean existsByName(String name);
}
