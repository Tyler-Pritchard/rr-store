package com.rr.store.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfigTestStub {

    @Bean
    public String dummyBean() {
        return "dummy";
    }
}
