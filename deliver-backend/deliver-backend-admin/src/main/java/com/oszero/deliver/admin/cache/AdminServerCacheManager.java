package com.oszero.deliver.admin.cache;

import com.oszero.deliver.admin.util.AdminRedisUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 服务端缓存清除
 *
 * @author oszero
 * @version 1.0.0
 */
@Component
@RequiredArgsConstructor
public class AdminServerCacheManager {

    private final AdminRedisUtils adminRedisUtils;

    /**
     * 清空模板缓存
     */
    public void evictTemplate(Long templateId) {
        adminRedisUtils.deleteByKey(CacheConstant.REDIS_CACHE_PREFIX
                + CacheConstant.TEMPLATE_CACHE_NAME
                + "::" + templateId);
    }

    /**
     * 清空模板应用缓存
     */
    public void evictTemplateApp(Long templateId) {
        adminRedisUtils.deleteByKey(CacheConstant.REDIS_CACHE_PREFIX
                + CacheConstant.TEMPLATE_APP_CACHE_NAME
                + "::" + templateId);
    }

    /**
     * 清空应用缓存
     */
    public void evictApp(Long appId) {
        adminRedisUtils.deleteByKey(CacheConstant.REDIS_CACHE_PREFIX
                + CacheConstant.APP_CACHE_NAME
                + "::" + appId);
    }
}
