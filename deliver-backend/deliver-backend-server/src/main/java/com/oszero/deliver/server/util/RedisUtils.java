package com.oszero.deliver.server.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Redis 工具类
 *
 * @author oszero
 * @version 1.0.0
 */
@Component
@RequiredArgsConstructor
public class RedisUtils {
    private final StringRedisTemplate stringRedisTemplate;

    public void setStrValue(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    public void setStrValueExpire(String key, String value, long timeout, TimeUnit unit) {
        stringRedisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    public String getStrValue(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    public boolean executeLuaScript(String luaScript, List<String> keys, Object... args) {
        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptText(luaScript);
        redisScript.setResultType(Boolean.class);
        Boolean result = stringRedisTemplate.execute(redisScript, keys, args);
        return result != null && result;
    }
}
