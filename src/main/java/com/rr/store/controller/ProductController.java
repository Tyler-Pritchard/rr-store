package com.rr.store.controller;

import com.rr.store.exception.ResourceNotFoundException;
import com.rr.store.model.Product;
import com.rr.store.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Public endpoint for fetching all products (unauthenticated)
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return ResponseEntity.ok(products);
    }

    // Public endpoint for fetching a single product by ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return ResponseEntity.ok(product);
    }

    // Admin-only endpoint to create a new product
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')") // Requires the user to be an admin
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product,
                                                 UriComponentsBuilder uriComponentsBuilder) {
        Product savedProduct = productRepository.save(product);
        URI location = uriComponentsBuilder.path("/api/products/{id}")
                                           .buildAndExpand(savedProduct.getId())
                                           .toUri();
        return ResponseEntity.created(location).body(savedProduct);
    }

    // Admin-only endpoint to update product details
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')") // Requires the user to be an admin
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @Valid @RequestBody Product productDetails) {
        return productRepository.findById(id)
            .map(product -> {
                product.setName(productDetails.getName());
                product.setDescription(productDetails.getDescription());
                product.setPrice(productDetails.getPrice());
                product.setStock(productDetails.getStock());
                product.setCategory(productDetails.getCategory());
                product.setImageUrl(productDetails.getImageUrl());
                Product updatedProduct = productRepository.save(product);
                return ResponseEntity.ok(updatedProduct);
            })
            .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    // Admin-only endpoint to delete a product
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')") // Requires the user to be an admin
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        return productRepository.findById(id)
            .map(product -> {
                productRepository.deleteById(id);
                return ResponseEntity.noContent().<Void>build();
            })
            .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }
}
