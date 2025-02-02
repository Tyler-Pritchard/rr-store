package com.rr.store.controller;

import io.micrometer.core.instrument.MeterRegistry;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomMetricsController {

    private final MeterRegistry meterRegistry;

    public CustomMetricsController(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }
    
    @GetMapping("/custom-metrics")
    public String getCustomMetrics() {
        return meterRegistry.toString();
    }    
}
