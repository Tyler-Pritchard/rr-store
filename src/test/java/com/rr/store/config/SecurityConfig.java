package com.rr.store.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration for the application.
 * 
 * Configures the Spring Security filter chain for non-test profiles.
 * Ensures security rules are applied appropriately based on the environment.
 */
@Configuration
@Profile("!test") // Exclude this configuration when the "test" profile is active
public class SecurityConfig {

    /**
     * Defines the security filter chain for the application.
     * 
     * @param http the HttpSecurity object to configure security settings
     * @return the configured SecurityFilterChain
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Configure HTTP security as needed; currently, no rules are defined.
        return http.build();
    }
}
