package com.oszero.deliver.admin.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * redis 工具类
 *
 * @author oszero
 * @version 1.0.0
 */
@Component
@RequiredArgsConstructor
public class AdminRedisUtils {
    private final StringRedisTemplate stringRedisTemplate;

    public Boolean deleteByKey(String key) {
        return stringRedisTemplate.delete(key);
    }
}
