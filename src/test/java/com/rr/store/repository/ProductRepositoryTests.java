package com.rr.store.repository;

import com.rr.store.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@ActiveProfiles("test") // Ensures test profile-specific properties are used
@Transactional // Ensures each test runs in isolation with a rollback
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void testFindAllProducts() {
        Product testProduct = new Product();
        testProduct.setName("Test Product");
        testProduct.setPrice(9.99);
        testProduct.setStock(100); // Example value
        testProduct.setCategory("Test Category"); // Add a category
        productRepository.save(testProduct);

        List<Product> products = productRepository.findAll();
        assertFalse(products.isEmpty(), "The product list should not be empty");
    }
}
