package com.rr.store;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rr.store.model.Product;
import com.rr.store.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.EventListener;
import org.springframework.boot.context.event.ApplicationReadyEvent;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@SpringBootApplication
public class RrStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(RrStoreApplication.class, args);
	}

	@Autowired
	private ProductRepository productRepository;

	@EventListener(ApplicationReadyEvent.class)
	public void seedDatabase() {
		if (productRepository.count() == 0) {
			ObjectMapper objectMapper = new ObjectMapper();
			try (InputStream inputStream = getClass().getResourceAsStream("/data/merch.json")) {
				if (inputStream == null) {
					System.out.println("Could not find merch.json file.");
					return;
				}
				List<Product> products = objectMapper.readValue(inputStream, new TypeReference<List<Product>>() {});
				productRepository.saveAll(products);
				System.out.println("Database seeded with products from merch.json.");
			} catch (IOException e) {
				System.err.println("Error reading merch.json: " + e.getMessage());
			}
		} else {
			System.out.println("Database already contains products. Skipping seeding.");
		}
	}
}
