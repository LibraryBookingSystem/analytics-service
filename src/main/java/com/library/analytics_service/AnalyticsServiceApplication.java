package com.library.analytics_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Main application class for Analytics Service
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.library.analytics_service", "com.library.common"})
public class AnalyticsServiceApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(AnalyticsServiceApplication.class, args);
    }
}





