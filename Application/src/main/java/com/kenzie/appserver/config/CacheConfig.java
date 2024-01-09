package com.kenzie.appserver.config;

import com.kenzie.appserver.cache.CacheClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cache.annotation.EnableCaching;

@Configuration
@EnableCaching
public class CacheConfig {

    // Create a Cache here if needed

    @Bean
    public CacheClient myCache() {
        return new CacheClient();
    }
}
