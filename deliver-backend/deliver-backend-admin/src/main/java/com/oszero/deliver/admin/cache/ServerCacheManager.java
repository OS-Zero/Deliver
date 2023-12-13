package com.oszero.deliver.admin.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 服务端缓存清除
 *
 * @author oszero
 * @version 1.0.0
 */
@Component
@RequiredArgsConstructor
public class ServerCacheManager {

    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 清空缓存
     */
    public void evictTemplate(Long templateId) {
        stringRedisTemplate.delete(CacheConstant.REDIS_CACHE_PREFIX
                + CacheConstant.TEMPLATE_CACHE_NAME
                + "::" + templateId);
    }

    public void evictTemplateApp(Long templateId) {
        stringRedisTemplate.delete(CacheConstant.REDIS_CACHE_PREFIX
                + CacheConstant.TEMPLATE_APP_CACHE_NAME
                + "::" + templateId);
    }

    public void evictApp(Long appId) {
        stringRedisTemplate.delete(CacheConstant.REDIS_CACHE_PREFIX
                + CacheConstant.APP_CACHE_NAME
                + "::" + appId);
    }
}
