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
                String allowedOriginsEnv = System.getenv("ALLOWED_ORIGINS");
                String[] allowedOrigins = allowedOriginsEnv != null 
                        ? allowedOriginsEnv.split(",") 
                        : new String[]{
                            "https://rrsite.vercel.app", 
                            "https://www.robrich.band",  
                            "https://rr-auth-production.up.railway.app", 
                            "https://rr-store-production.up.railway.app", 
                            "http://localhost:3000", 
                            "http://localhost:8080"
                        };

                registry.addMapping("/**")
                        .allowedOrigins(allowedOrigins)
                        .allowedMethods(
                                "GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"
                        )
                        .allowedHeaders(
                                "Content-Type", "Authorization", "X-Requested-With", "Accept", "Origin", "Access-Control-Allow-Origin"
                        )
                        .exposedHeaders("Access-Control-Allow-Origin")
                        .allowCredentials(true);
            }
        };
    }
}
