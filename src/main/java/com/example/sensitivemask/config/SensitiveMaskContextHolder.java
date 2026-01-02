package com.example.sensitivemask.config;

import org.springframework.stereotype.Component;

@Component
public class SensitiveMaskContextHolder {
    private final SensitiveMaskProperties properties;

    public SensitiveMaskContextHolder(SensitiveMaskProperties properties) {
        this.properties = properties;
    }

    public boolean isEnabled() {
        return properties.isEnabled();
    }
}