package com.rr.store;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rr.store.model.Product;
import com.rr.store.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.EventListener;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class RrStoreApplication {

    public static void main(String[] args) {
        // Start Spring Boot application and capture the context
        ConfigurableApplicationContext context = SpringApplication.run(RrStoreApplication.class, args);

        // Access the environment and log active profiles and key properties
        Environment env = context.getEnvironment();
        System.out.println("======== Application Startup Configuration ========");
        System.out.println("Active Profiles: " + Arrays.toString(env.getActiveProfiles()));
        System.out.println("Datasource URL: " + env.getProperty("spring.datasource.url"));
        System.out.println("===================================================");
    }

    @Autowired
    private ProductRepository productRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void seedDatabase() {
        if (productRepository.count() == 0) {
            ObjectMapper objectMapper = new ObjectMapper();
            try (InputStream inputStream = getClass().getResourceAsStream("/data/merch.json")) {
                if (inputStream == null) {
                    System.err.println("Could not find merch.json file.");
                    return;
                }
                List<Product> products = objectMapper.readValue(inputStream, new TypeReference<List<Product>>() {});
                productRepository.saveAll(products);
                System.out.println("Database seeded with products from merch.json.");
            } catch (IOException e) {
                System.err.println("Error reading merch.json: " + e.getMessage());
            }
        } else {
            System.out.println("Database already contains products. Skipping seeding.");
        }
    }
}
