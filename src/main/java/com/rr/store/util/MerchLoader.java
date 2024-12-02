package com.rr.store.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rr.store.model.Product;
import com.rr.store.repository.ProductRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.List;

@Component
public class MerchLoader {

    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;

    public MerchLoader(ProductRepository productRepository) {
        this.productRepository = productRepository;
        this.objectMapper = new ObjectMapper();
    }

    @PostConstruct // Runs automatically after application startup
    public void loadMerchData() {
        try {
            // Load the merch.json file
            File jsonFile = new File("src/main/resources/data/merch.json");
            if (!jsonFile.exists()) {
                System.out.println("merch.json file not found!");
                return;
            }

            // Parse the JSON file
            List<Product> products = objectMapper.readValue(jsonFile, new TypeReference<List<Product>>() {});
            
            // Sync with the database
            products.forEach(this::syncProduct);
            System.out.println("Database successfully synced with merch.json");

        } catch (Exception e) {
            System.err.println("Error loading merch.json: " + e.getMessage());
        }
    }

    private void syncProduct(Product newProduct) {
        // Find by name and update if exists, else insert
        productRepository.findByName(newProduct.getName())
                .ifPresentOrElse(existingProduct -> {
                    existingProduct.setDescription(newProduct.getDescription());
                    existingProduct.setPrice(newProduct.getPrice());
                    existingProduct.setStock(newProduct.getStock());
                    existingProduct.setCategory(newProduct.getCategory());
                    existingProduct.setImageUrl(newProduct.getImageUrl());
                    productRepository.save(existingProduct); // Update existing product
                }, () -> productRepository.save(newProduct)); // Insert new product
    }
}
