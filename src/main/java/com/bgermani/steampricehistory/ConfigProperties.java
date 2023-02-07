package com.bgermani.steampricehistory;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ConfigurationProperties()
@EnableScheduling
public class ConfigProperties {
    
}
