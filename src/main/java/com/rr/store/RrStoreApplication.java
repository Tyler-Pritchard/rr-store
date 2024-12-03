package com.rr.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.util.Arrays;

/**
 * Entry point for the RR Store application.
 * 
 * Configures and starts the Spring Boot application and logs essential
 * startup information such as active profiles and database connection details.
 */
@SpringBootApplication
public class RrStoreApplication {

    public static void main(String[] args) {
        // Start Spring Boot application and capture the context
        ConfigurableApplicationContext context = SpringApplication.run(RrStoreApplication.class, args);

        // Access the environment and log active profiles and key properties
        Environment env = context.getEnvironment();
        logStartupConfiguration(env);
    }

    /**
     * Logs the application's startup configuration details.
     * 
     * @param env the Spring Environment providing configuration properties
     */
    private static void logStartupConfiguration(Environment env) {
        System.out.println("======== Application Startup Configuration ========");
        System.out.println("Active Profiles: " + Arrays.toString(env.getActiveProfiles()));
        System.out.println("Datasource URL: " + env.getProperty("spring.datasource.url"));
        System.out.println("===================================================");
    }
}
