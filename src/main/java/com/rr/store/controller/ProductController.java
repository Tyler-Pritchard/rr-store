package com.rr.store.controller;

import com.rr.store.domain.model.Product;
import com.rr.store.domain.service.ProductService;
import com.rr.store.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * REST controller for managing products.
 * 
 * Handles HTTP requests related to products and delegates
 * business logic to the ProductService.
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    /**
     * Constructor to inject dependencies.
     * 
     * @param productService the service layer for product-related operations
     */
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Retrieves all available products.
     * 
     * @return a list of all products
     */
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.findAllProducts();
        return ResponseEntity.ok(products);
    }

    /**
     * Retrieves a single product by its ID.
     * 
     * @param id the ID of the product to retrieve
     * @return the product with the given ID
     * @throws ResourceNotFoundException if no product with the given ID is found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.findProductById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return ResponseEntity.ok(product);
    }

    /**
     * Creates a new product.
     * 
     * @param product the product to create
     * @param uriComponentsBuilder a utility to build the location URI
     * @return the created product with its location URI
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product,
                                                 UriComponentsBuilder uriComponentsBuilder) {
        Product savedProduct = productService.createProduct(product);
        URI location = uriComponentsBuilder.path("/api/products/{id}")
                                           .buildAndExpand(savedProduct.getId())
                                           .toUri();
        return ResponseEntity.created(location).body(savedProduct);
    }

    /**
     * Updates an existing product.
     * 
     * @param id the ID of the product to update
     * @param productDetails the updated product details
     * @return the updated product
     * @throws ResourceNotFoundException if no product with the given ID is found
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @Valid @RequestBody Product productDetails) {
        Product updatedProduct = productService.updateProduct(id, productDetails);
        return ResponseEntity.ok(updatedProduct);
    }

    /**
     * Deletes a product by its ID.
     * 
     * @param id the ID of the product to delete
     * @return a response indicating the product has been deleted
     * @throws ResourceNotFoundException if no product with the given ID is found
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
