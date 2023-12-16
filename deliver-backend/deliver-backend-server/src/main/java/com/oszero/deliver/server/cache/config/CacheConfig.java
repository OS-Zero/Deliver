package com.oszero.deliver.server.cache.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.oszero.deliver.server.cache.constant.CacheConstant;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 缓存配置
 *
 * @author oszero
 * @version 1.0.0
 */
@Configuration
@ConditionalOnMissingBean({CacheManager.class})
public class CacheConfig {

    /**
     * Caffeine 缓存管理器
     */
    @Bean
    public CacheManager caffeineCacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();

        List<CaffeineCache> caches = new ArrayList<>(CacheConstant.CacheEnum.values().length);

        for (var cache : CacheConstant.CacheEnum.values()) {

            Caffeine<Object, Object> caffeine = Caffeine.newBuilder().recordStats()
                    .maximumSize(cache.getMaxSize());
            if (cache.getTtl() > 0) { // 缓存时间设置，分钟级别
                caffeine.expireAfterWrite(Duration.ofMinutes(cache.getTtl()));
            }
            caches.add(new CaffeineCache(cache.getName(), caffeine.build()));
        }

        cacheManager.setCaches(caches);
        return cacheManager;
    }

    /**
     * Redis 缓存管理器
     */
    @Bean
    @Primary
    public CacheManager redisCacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(
                connectionFactory);

        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                .prefixCacheNameWith(CacheConstant.REDIS_CACHE_PREFIX);

        Map<String, RedisCacheConfiguration> cacheMap = new LinkedHashMap<>(CacheConstant.CacheEnum.values().length);

        for (var cache : CacheConstant.CacheEnum.values()) {

            RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                    .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                    .prefixCacheNameWith(CacheConstant.REDIS_CACHE_PREFIX);

            if (cache.getTtl() > 0) { // 缓存时间设置，分钟级别
                redisCacheConfiguration = redisCacheConfiguration.entryTtl(Duration.ofMinutes(cache.getTtl()));
            }
            cacheMap.put(cache.getName(), redisCacheConfiguration);
        }

        RedisCacheManager redisCacheManager = new RedisCacheManager(redisCacheWriter,
                defaultCacheConfig, cacheMap);
        redisCacheManager.setTransactionAware(true); // 事务
        redisCacheManager.initializeCaches(); // 初始化
        return redisCacheManager;
    }
}
