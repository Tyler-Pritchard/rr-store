package com.rr.store.util;

import com.rr.store.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ProductJsonReaderTests {

    @Autowired
    private ProductJsonReader productJsonReader;

    @Test
    void testReadProductsFromFile() throws Exception {
        List<Product> products = productJsonReader.readProductsFromFile();
        assertNotNull(products);
        System.out.println("Products loaded: " + products.size());
    }
}
