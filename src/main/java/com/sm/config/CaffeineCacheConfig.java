package com.sm.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

@EnableCaching
@Configuration
public class CaffeineCacheConfig {

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager manager = new CaffeineCacheManager();
        manager.registerCustomCache("employees", employeeCache());
        manager.registerCustomCache("customers", customerCache());

        // to avoid dynamic caches and be sure each name is assigned to a specific config (dynamic = false)
        // throws error when tries to use a new cache
        manager.setCacheNames(Collections.emptyList());
        return manager;
    }

    private static Cache<Object, Object> employeeCache() {
        return Caffeine.newBuilder().maximumSize(1000).expireAfterWrite(2, TimeUnit.MINUTES).build();
    }

    private static Cache<Object, Object> customerCache() {
        return Caffeine.newBuilder().maximumSize(5000).expireAfterWrite(5, TimeUnit.MINUTES).build();
    }

}
