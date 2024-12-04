package com.rr.store;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for the RR Store application.
 * 
 * Validates that the application context loads successfully and 
 * basic application-level configurations are correct.
 */
@SpringBootTest(classes = {RrStoreApplication.class})
@ActiveProfiles("test") // Ensures the "test" profile is active during tests
public class RrStoreApplicationTests {

    @Autowired
    private Environment environment; // Inject Spring Environment

    /**
     * Ensures the Spring application context loads without errors.
     */
    @Test
    void contextLoads() {
        // Simple assertion to confirm context loading
        assertThat(true).isTrue();
    }

    /**
     * Validates that the "test" profile is active during the tests.
     */
    @Test
    void applicationStartsUpCorrectly() {
        // Validate that the active profile is "test"
        String[] activeProfiles = environment.getActiveProfiles();
        assertThat(activeProfiles).contains("test");
    }
}
