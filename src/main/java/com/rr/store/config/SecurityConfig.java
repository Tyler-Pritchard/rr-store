package com.rr.store.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity (enable for production)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/products/**").permitAll() // Publicly accessible endpoints for products
                .requestMatchers("/api/cart/**").permitAll() // Publicly accessible endpoints for cart operations
                .requestMatchers("/api/admin/**").hasRole("ADMIN") // Admin endpoints require ADMIN role
                .anyRequest().authenticated() // All other requests require authentication
            )
            .httpBasic(); // Simplified method for HTTP Basic authentication

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Use BCrypt for password hashing
    }
}
