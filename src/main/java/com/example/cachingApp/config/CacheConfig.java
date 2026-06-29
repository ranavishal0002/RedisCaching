package com.example.cachingApp.config;


import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("employees");
    }
}

/********************
 1.  CachManager :- is the storing Area in the backend it's actually holding the data
                    and that area could be anything like it could be in Memory, Redis, Ehcache
                    and keeps everything in concurrent HashMap(internally)

 ============================================
 Here in this code  we are using in Memory Cache us concurrent HashMap
 and CachManager is creating a region internally namely called "employees".


 */
