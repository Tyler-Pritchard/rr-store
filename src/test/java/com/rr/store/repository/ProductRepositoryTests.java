package com.rr.store.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.rr.store.domain.model.Product;
import com.rr.store.infrastructure.repository.ProductRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for the ProductRepository.
 * 
 * Validates repository methods for managing Product entities,
 * ensuring correct database interactions.
 */
@SpringBootTest
@ActiveProfiles("test") // Ensures test profile-specific properties are used
@Transactional // Ensures each test runs in isolation with a rollback
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository productRepository;

    /**
     * Tests the retrieval of all products from the database.
     * 
     * Ensures that the repository can save and retrieve products correctly.
     */
    @Test
    void testFindAllProducts() {
        // Create and save a test product
        Product testProduct = createTestProduct("Test Product", 9.99, 100, "Test Category");
        productRepository.save(testProduct);

        // Retrieve all products
        List<Product> products = productRepository.findAll();

        // Assertions
        assertThat(products).isNotEmpty();
        assertThat(products).anyMatch(product -> "Test Product".equals(product.getName()));
    }

    /**
     * Tests the retrieval of a product by name.
     * 
     * Ensures that the repository can correctly find a product by its name.
     */
    @Test
    void testFindByName() {
        // Create and save a test product
        Product testProduct = createTestProduct("Unique Product", 19.99, 50, "Unique Category");
        productRepository.save(testProduct);

        // Retrieve the product by name
        Product retrievedProduct = productRepository.findByName("Unique Product").orElse(null);

        // Assertions
        assertThat(retrievedProduct).isNotNull();
        assertThat(retrievedProduct.getName()).isEqualTo("Unique Product");
        assertThat(retrievedProduct.getPrice()).isEqualTo(19.99);
    }

    /**
     * Helper method to create a Product instance.
     * 
     * @param name     the product's name
     * @param price    the product's price
     * @param stock    the product's stock count
     * @param category the product's category
     * @return a new Product instance
     */
    private Product createTestProduct(String name, double price, int stock, String category) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setStock(stock);
        product.setCategory(category);
        return product;
    }
}
