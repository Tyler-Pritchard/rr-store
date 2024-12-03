package com.rr.store;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for the RR Store application.
 * 
 * Validates that the application context loads successfully and basic
 * application-level configurations are correct.
 */
@SpringBootTest(classes = {RrStoreApplication.class})
@ActiveProfiles("test") // Ensures the "test" profile is active during the tests
public class RrStoreApplicationTests {

    /**
     * Ensures the Spring application context loads without errors.
     */
    @Test
    void contextLoads() {
        // Simple assertion to confirm context loading
        assertThat(true).isTrue();
    }

    /**
     * Placeholder for additional application-level tests.
     * 
     * Example: Testing environment-specific properties or other high-level configurations.
     */
    @Test
    void applicationStartsUpCorrectly() {
        // Add any additional startup-related checks here
        assertThat(System.getProperty("spring.profiles.active")).isEqualTo("test");
    }
}
