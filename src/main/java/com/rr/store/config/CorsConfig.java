package com.rr.store.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry.addMapping("/**") // Allow all endpoints
                        .allowedOrigins(
                                "https://rrsite.vercel.app", // Production frontend
                                "https://www.robrich.band",  // Production domain
                                "https://rr-auth-production.up.railway.app", // Auth service
                                "https://rr-store-production.up.railway.app", // This service
                                "http://localhost:3000", // Development frontend
                                "http://localhost:8080"  // Other local services
                        )
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allow HTTP methods
                        .allowedHeaders("*") // Allow all headers
                        .allowCredentials(true); // Allow cookies and credentials
            }
        };
    }
}
