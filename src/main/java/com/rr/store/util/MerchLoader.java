package com.rr.store.util;

import com.rr.store.domain.model.Product;
import com.rr.store.infrastructure.repository.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Utility class for synchronizing product data with the database.
 * 
 * The MerchLoader ensures that products from an external source are
 * persisted in the database without creating duplicates.
 */
@Component
public class MerchLoader {

    private final ProductRepository productRepository;

    /**
     * Constructor to inject the ProductRepository dependency.
     * 
     * @param productRepository the repository for managing product data
     */
    public MerchLoader(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Synchronizes a list of products with the database.
     * 
     * - If a product with the same name already exists, it is ignored.
     * - If a product does not exist, it is added to the database.
     * 
     * @param products the list of products to synchronize
     * @throws IllegalArgumentException if the input list is null or contains invalid products
     */
    public void syncProducts(List<Product> products) {
        if (products == null || products.isEmpty()) {
            throw new IllegalArgumentException("The product list cannot be null or empty.");
        }

        for (Product product : products) {
            if (product == null || product.getName() == null || product.getName().isBlank()) {
                throw new IllegalArgumentException("Each product must have a valid, non-blank name.");
            }

            // Save the product if it does not already exist
            productRepository.findByName(product.getName()).orElseGet(() -> productRepository.save(product));
        }
    }
}
