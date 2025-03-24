package com.rr.store.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    private static final String[] DEFAULT_ALLOWED_ORIGINS = {
        "https://rrsite.vercel.app",
        "https://www.robrich.band",
        "https://rr-auth-production.up.railway.app",
        "https://rr-store-production.up.railway.app",
        "http://host.docker.internal:3000",
        "http://host.docker.internal:8080",
        "http://localhost:3000"
    };

    @Bean
    WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                // Load allowed origins from environment or fallback to default
                String[] allowedOrigins = getAllowedOrigins();

                registry.addMapping("/**")
                        .allowedOrigins(allowedOrigins)
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD")
                        .allowedHeaders("Content-Type", "Authorization", "X-Requested-With", "Accept", "Origin")
                        .exposedHeaders("Authorization")
                        .allowCredentials(true)
                        .maxAge(3600); // Cache preflight requests for 1 hour
            }
        };
    }

    /**
     * Retrieves allowed origins from the environment or uses defaults.
     * 
     * @return Array of allowed origins
     */
    private String[] getAllowedOrigins() {
        String allowedOriginsEnv = System.getenv("ALLOWED_ORIGINS");
        if (allowedOriginsEnv != null && !allowedOriginsEnv.isBlank()) {
            return allowedOriginsEnv.split(",");
        }
        return DEFAULT_ALLOWED_ORIGINS;
    }
}
