package com.uos.dsd.cinema.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "storage")
@EnableConfigurationProperties
@Getter
@Setter
public class StorageConfig {
    
    private String rootPath;
    private String urlPrefix;
} 
