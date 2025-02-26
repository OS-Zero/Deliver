/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.oszero.deliver.business.common.util;

import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author oszero
 * @version 1.0.0
 */
@Component
@RequiredArgsConstructor
public class RedisUtils {
    private final StringRedisTemplate stringRedisTemplate;

    public Boolean deleteByKey(String key) {
        return stringRedisTemplate.delete(key);
    }

    public String getStringByKey(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    public <T> T getJsonObjByKey(String key, Class<T> clazz) {
        String jsonStr = stringRedisTemplate.opsForValue().get(key);
        if (Objects.isNull(jsonStr)) {
            return null;
        }
        return JSONUtil.toBean(jsonStr, clazz);
    }

    public void savaStringByKeyAndExpireTime(String key, String value, long expireTime, TimeUnit timeUnit) {
        stringRedisTemplate.opsForValue().set(key, value, expireTime, timeUnit);
    }

    public void savaStringByKeyNoExpireTime(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    public void saveJsonObjByKeyAndExpireTime(String key, Object value, long expireTime, TimeUnit timeUnit) {
        String jsonStr = JSONUtil.toJsonStr(value);
        stringRedisTemplate.opsForValue().set(key, jsonStr, expireTime, timeUnit);
    }

    public void saveJsonObjByKeyNoExpireTime(String key, Object value) {
        String jsonStr = JSONUtil.toJsonStr(value);
        stringRedisTemplate.opsForValue().set(key, jsonStr);
    }
}
