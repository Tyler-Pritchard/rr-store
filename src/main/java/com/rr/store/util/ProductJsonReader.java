package com.rr.store.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rr.store.domain.model.Product;

import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

/**
 * Utility class for reading products from a JSON file.
 * 
 * Uses Jackson to parse a JSON file into a list of Product objects.
 */
@Component
public class ProductJsonReader {

    private static final String DEFAULT_FILE_PATH = "/data/merch.json";

    private final ObjectMapper objectMapper;

    /**
     * Constructor to inject dependencies.
     * 
     * @param objectMapper the ObjectMapper used for JSON parsing
     */
    public ProductJsonReader(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Reads products from a specified JSON file.
     * 
     * @return a list of Product objects
     * @throws IllegalStateException if the file cannot be read or parsed
     */
    public List<Product> readProductsFromFile() {
        try (InputStream is = getClass().getResourceAsStream(DEFAULT_FILE_PATH)) {
            if (is == null) {
                throw new IllegalStateException("Could not find the file at: " + DEFAULT_FILE_PATH);
            }

            return objectMapper.readValue(is, new TypeReference<List<Product>>() {});
        } catch (Exception e) {
            throw new IllegalStateException("Failed to read or parse products from file: " + DEFAULT_FILE_PATH, e);
        }
    }
}
