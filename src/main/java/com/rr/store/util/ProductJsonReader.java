package com.rr.store.util;

import com.rr.store.domain.model.Product;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * A utility class for reading product data from a JSON file.
 */
@Component
public class ProductJsonReader {

    private final ObjectMapper objectMapper;
    private final FileReaderService fileReaderService;

    public ProductJsonReader(ObjectMapper objectMapper, FileReaderService fileReaderService) {
        this.objectMapper = objectMapper;
        this.fileReaderService = fileReaderService;
    }

    /**
     * Reads products from the JSON file and returns them as a list.
     *
     * @return a list of products
     * @throws IllegalStateException if the file is missing or cannot be parsed
     */
    public List<Product> readProductsFromFile() {
        try {
            String jsonContent = fileReaderService.readJsonFile("src/main/resources/products.json");
            return objectMapper.readValue(jsonContent, new TypeReference<List<Product>>() {});
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read or parse products", e);
        }
    }    
}
