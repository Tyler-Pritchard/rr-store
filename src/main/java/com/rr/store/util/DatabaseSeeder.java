package com.rr.store.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rr.store.domain.model.Product;
import com.rr.store.infrastructure.repository.ProductRepository;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.boot.context.event.ApplicationReadyEvent;

import java.io.InputStream;
import java.util.List;

/**
 * Handles database seeding at application startup.
 * 
 * Seeds the database with products from a JSON file if no products are present.
 */
@Component
public class DatabaseSeeder {

    private static final String DATA_FILE_PATH = "/data/merch.json";

    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;

    public DatabaseSeeder(ProductRepository productRepository, ObjectMapper objectMapper) {
        this.productRepository = productRepository;
        this.objectMapper = objectMapper;
    }

    /**
     * Seeds the database with products after the application is fully started.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void seedDatabase() {
        if (productRepository.count() > 0) {
            System.out.println("Database already contains products. Skipping seeding.");
            return;
        }

        try (InputStream inputStream = getClass().getResourceAsStream(DATA_FILE_PATH)) {
            if (inputStream == null) {
                System.err.println("Could not find data file: " + DATA_FILE_PATH);
                return;
            }

            List<Product> products = objectMapper.readValue(inputStream, new TypeReference<List<Product>>() {});
            productRepository.saveAll(products);
            System.out.println("Database seeded with products from " + DATA_FILE_PATH);
        } catch (Exception e) {
            System.err.println("Error seeding database: " + e.getMessage());
        }
    }
}
