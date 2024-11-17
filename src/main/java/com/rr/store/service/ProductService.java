package com.rr.store.service;

import com.rr.store.model.Product;
import com.rr.store.repository.ProductRepository;
import com.rr.store.util.ProductJsonReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductJsonReader productJsonReader;

    // Load products from JSON and save them to the database
    @PostConstruct
    public void init() {
        try {
            List<Product> products = productJsonReader.readProductsFromFile();
            productRepository.saveAll(products);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
