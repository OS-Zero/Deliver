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

package com.oszero.deliver.server.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.stream.Record;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.core.StreamOperations;
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

    public RecordId sendMessage(String streamKey, String messageValue) {

        // 使用 StringRedisTemplate 获取 StreamOperations 对象
        StreamOperations<String, Object, Object> streamOperations = stringRedisTemplate.opsForStream();

        // 创建 Stream 并发布消息
        return streamOperations.add(Record.of(messageValue).withStreamKey(streamKey));
    }
}
