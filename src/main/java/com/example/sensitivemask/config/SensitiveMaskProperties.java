package com.example.sensitivemask.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "sensitive-mask")
public class SensitiveMaskProperties {

    /**
     * 是否启用敏感数据脱敏功能，默认 true
     */
    private boolean enabled = true;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}