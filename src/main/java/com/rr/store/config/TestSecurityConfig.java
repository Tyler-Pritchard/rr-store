package com.rr.store.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration for testing environments.
 * 
 * This configuration disables CSRF and permits all requests, simplifying integration testing.
 */
@Configuration
public class TestSecurityConfig {

    /**
     * Defines a permissive security filter chain for tests.
     *
     * @param http the HttpSecurity configuration
     * @return the configured SecurityFilterChain
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain testSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // CSRF disabled for testing simplicity
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()); // Permit all requests

        return http.build();
    }
}
