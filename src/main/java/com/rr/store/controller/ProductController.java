package com.rr.store.controller;

import com.rr.store.model.Product;
import com.rr.store.repository.ProductRepository;
import com.rr.store.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return ResponseEntity.ok(products);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product,
                                                 UriComponentsBuilder uriComponentsBuilder) {
        Product savedProduct = productRepository.save(product);
        URI location = uriComponentsBuilder.path("/api/products/{id}")
                                           .buildAndExpand(savedProduct.getId())
                                           .toUri();
        return ResponseEntity.created(location).body(savedProduct);
    }

    @PutMapping("/{id}")
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        return productRepository.findById(id)
            .map(product -> {
                productRepository.deleteById(id);
                return ResponseEntity.noContent().<Void>build();
            })
            .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }
}
