package com.example.cachingApp.config;


import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import java.time.Duration;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
        public CacheManager cacheManager(RedisConnectionFactory factory) {
        RedisCacheConfiguration config = RedisCacheConfiguration
                .defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(60))  // cache expires after 10 mins
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair
                                .fromSerializer(new GenericJackson2JsonRedisSerializer())
                );

        return RedisCacheManager.builder(factory)
                .cacheDefaults(config)
                .build();
    }
}

/********************
 1.  CachManager :- is the storing Area in the backend it's actually holding the data
                    and that area could be anything like it could be in Memory, Redis, Ehcache
                    and keeps everything in concurrent HashMap(internally)

 ============================================
 Here in this code  we are using in Memory Cache us concurrent HashMap
 and CachManager is creating a region internally namely called "employees".

 =====================================================================
 Previoulsy I was doing in-memory Caching
  using =>
 @Bean
 public CacheManager cacheManager() {
 return new ConcurrentMapCacheManager("employees"); // ← this is blocking Redis
 }

 but for Redis Caching we need to define diff caching config which I have defined now
 +++++++++++++++++++++++++++++++++++++++++++
 IMPORTANT NOTE:-
 RedisConnectionFactory factory   // Spring auto-injects Redis connection
 entryTtl(Duration.ofMinutes(10)) // data auto-expires after 10 mins
 GenericJackson2JsonRedisSerializer() // stores data as readable JSON in Redis
 RedisCacheManager.builder(factory)   // builds CacheManager using Redis
 prefixCacheNameWith("employees") // used for naming the cached
 enableTimeToIdle()  // provide extra trime for the data which is being used by the user
 +++++++++++++++++++++++++++

 */
