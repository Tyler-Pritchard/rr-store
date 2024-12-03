package com.rr.store.domain.service;

import com.rr.store.domain.model.Product;
import com.rr.store.exception.ResourceNotFoundException;
import com.rr.store.infrastructure.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for managing products.
 * 
 * Encapsulates business logic for product-related operations,
 * including finding, creating, updating, and deleting products.
 */
@Service
public class ProductService {

    private final ProductRepository productRepository;

    /**
     * Constructor to inject dependencies.
     * 
     * @param productRepository the repository for accessing product data
     */
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Retrieves all products from the repository.
     * 
     * @return a list of all products
     */
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Retrieves a product by its ID.
     * 
     * @param id the ID of the product to retrieve
     * @return an Optional containing the product, if found
     */
    public Optional<Product> findProductById(Long id) {
        return productRepository.findById(id);
    }

    /**
     * Creates a new product and saves it to the repository.
     * 
     * @param product the product to create
     * @return the created product
     */
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    /**
     * Updates an existing product.
     * 
     * @param id the ID of the product to update
     * @param productDetails the updated product details
     * @return the updated product
     * @throws ResourceNotFoundException if no product with the given ID is found
     */
    public Product updateProduct(Long id, Product productDetails) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setName(productDetails.getName());
                    product.setDescription(productDetails.getDescription());
                    product.setPrice(productDetails.getPrice());
                    product.setStock(productDetails.getStock());
                    product.setCategory(productDetails.getCategory());
                    product.setImageUrl(productDetails.getImageUrl());
                    return productRepository.save(product);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    /**
     * Deletes a product by its ID.
     * 
     * @param id the ID of the product to delete
     * @throws ResourceNotFoundException if no product with the given ID is found
     */
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }
}
