package com.example.sensitivemask.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(name = "com.fasterxml.jackson.databind.ObjectMapper")
@EnableConfigurationProperties(SensitiveMaskProperties.class)
public class SensitiveDataMaskAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public SensitiveMaskContextHolder sensitiveMaskContextHolder(SensitiveMaskProperties properties) {
        return new SensitiveMaskContextHolder(properties);
    }
}