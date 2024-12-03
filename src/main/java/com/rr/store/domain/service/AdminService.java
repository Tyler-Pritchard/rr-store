package com.rr.store.domain.service;

import com.rr.store.domain.model.Product;
import com.rr.store.util.MerchLoader;
import com.rr.store.util.ProductJsonReader;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final MerchLoader merchLoader;
    private final ProductJsonReader productJsonReader;

    public AdminService(MerchLoader merchLoader, ProductJsonReader productJsonReader) {
        this.merchLoader = merchLoader;
        this.productJsonReader = productJsonReader;
    }

    /**
     * Reloads merch.json and syncs its data with the database.
     *
     * @throws Exception if reading or syncing the data fails
     */
    public void reloadMerch() throws Exception {
        // Read products from the JSON file
        List<Product> products = productJsonReader.readProductsFromFile();

        // Sync products with the database
        merchLoader.syncProducts(products);
    }
}
