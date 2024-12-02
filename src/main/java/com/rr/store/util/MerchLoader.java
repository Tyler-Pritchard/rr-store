package com.rr.store.util;

import com.rr.store.model.Product;
import com.rr.store.repository.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MerchLoader {

    private final ProductRepository productRepository;

    public MerchLoader(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void syncProducts(List<Product> products) {
        for (Product product : products) {
            productRepository.findByName(product.getName()).orElseGet(() -> productRepository.save(product));
        }
    }
}
