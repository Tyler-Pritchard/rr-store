package com.rr.store.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Test stub for security configuration.
 * 
 * This class provides a minimal configuration to bypass security during testing.
 * Additional test-specific beans can be added here as needed.
 */
@Configuration
public class SecurityConfigTestStub {

    /**
     * A dummy bean for testing purposes.
     * 
     * This bean serves as a placeholder and can be replaced with
     * more meaningful test-specific configurations if needed.
     * 
     * @return a dummy string
     */
    @Bean
    public String dummyBean() {
        return "dummy";
    }
}
