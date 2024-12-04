package com.rr.store.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rr.store.domain.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
public class ProductJsonReaderTests {

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private ProductJsonReader productJsonReader;

    @Mock
    private FileReaderService fileReaderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    
        // Configure the ObjectMapper mock
        when(objectMapper.getTypeFactory())
            .thenReturn(new ObjectMapper().getTypeFactory());
    }

    @Test
    void testReadProductsFromFile() throws IOException {
        String validJson = """
            [
                {
                    "name": "Forest Gothic Band Tee-M",
                    "description": "A soft cotton unisex t-shirt featuring Rob Rich's logo.",
                    "price": 25.0,
                    "stock": 150,
                    "category": "Apparel",
                    "imageUrl": "https://i.imgur.com/L1RZ47V.jpg"
                }
            ]
        """;

        when(fileReaderService.readJsonFile("src/main/resources/products.json")).thenReturn(validJson);
        when(objectMapper.readValue(validJson, objectMapper.getTypeFactory().constructCollectionType(List.class, Product.class)))
            .thenReturn(Collections.singletonList(new Product(1L, "Forest Gothic Band Tee-M", "A soft cotton unisex t-shirt featuring Rob Rich's logo.", 25.0, 150, "Apparel", "https://i.imgur.com/L1RZ47V.jpg")));

        List<Product> products = productJsonReader.readProductsFromFile();

        assertThat(products).isNotNull();
        assertThat(products).hasSize(1);
        assertThat(products.get(0).getId()).isEqualTo(1L); // Check the id
        assertThat(products.get(0).getName()).isEqualTo("Forest Gothic Band Tee-M");
    }

    @Test
    void testFileNotFound() throws IOException {
        when(fileReaderService.readJsonFile("src/main/resources/products.json"))
            .thenThrow(new IllegalStateException("Could not find file"));

        assertThatThrownBy(() -> productJsonReader.readProductsFromFile())
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("Could not find file");
    }

    @Test
    void testMalformedJsonFile() throws IOException {
        String malformedJson = "[{name: 'missing_quotes']"; // Invalid JSON
        when(fileReaderService.readJsonFile("src/main/resources/products.json")).thenReturn(malformedJson);
        when(objectMapper.readValue(malformedJson, objectMapper.getTypeFactory().constructCollectionType(List.class, Product.class)))
            .thenThrow(new IOException("Malformed JSON"));

        assertThatThrownBy(() -> productJsonReader.readProductsFromFile())
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("Failed to read or parse products");
    }
}
