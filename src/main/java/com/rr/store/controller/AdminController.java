package com.rr.store.controller;

import com.rr.store.model.Product;
import com.rr.store.util.MerchLoader;
import com.rr.store.util.ProductJsonReader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final MerchLoader merchLoader;
    private final ProductJsonReader productJsonReader;

    public AdminController(MerchLoader merchLoader, ProductJsonReader productJsonReader) {
        this.merchLoader = merchLoader;
        this.productJsonReader = productJsonReader;
    }

    /**
     * Endpoint to reload merch.json and sync it with the database
     */
    @PostMapping("/reload-merch")
    public ResponseEntity<String> reloadMerch() {
        try {
            // Use the existing method in ProductJsonReader to read products
            List<Product> products = productJsonReader.readProductsFromFile();

            // Sync the loaded products with the database
            merchLoader.syncProducts(products);

            return ResponseEntity.ok("Merch data reloaded successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Failed to reload merch data: " + e.getMessage());
        }
    }
}
