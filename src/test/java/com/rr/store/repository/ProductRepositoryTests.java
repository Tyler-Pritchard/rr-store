package com.rr.store.repository;

import com.rr.store.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void testFindAllProducts() {
        List<Product> products = productRepository.findAll();
        assertFalse(products.isEmpty(), "The product list should not be empty");
        System.out.println("Products retrieved: " + products.size());
    }
}
