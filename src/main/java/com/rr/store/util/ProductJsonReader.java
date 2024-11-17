package com.rr.store.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rr.store.model.Product;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
public class ProductJsonReader {

    public List<Product> readProductsFromFile() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        // Load the JSON file from resources
        InputStream is = getClass().getResourceAsStream("/data/merch.json");
        // Convert JSON into a list of Product objects
        return mapper.readValue(is, new TypeReference<List<Product>>() {});
    }
}
