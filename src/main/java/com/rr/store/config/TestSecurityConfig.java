package com.rr.store.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration for testing environments.
 * 
 * This configuration disables CSRF and permits all requests to Actuator and metrics endpoints,
 * simplifying integration testing and monitoring access.
 */
@Configuration
@EnableWebSecurity
public class TestSecurityConfig {

    /**
     * Defines a permissive security filter chain for Actuator and metrics endpoints.
     *
     * @param http the HttpSecurity configuration
     * @return the configured SecurityFilterChain
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())  
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/actuator/**",    
                    "/metrics",        
                    "/prometheus",     
                    "/custom-metrics"  // Add custom metrics explicitly
                ).permitAll()
                .anyRequest().authenticated()
            )
            .build();  
    }    
}
