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

package com.oszero.deliver.server.cache.constant;

import lombok.Getter;

/**
 * 缓存常量
 *
 * @author oszero
 * @version 1.0.0
 */
public class CacheConstant {

    /**
     * 本项目 Redis 缓存前缀
     */
    public static final String REDIS_CACHE_PREFIX = "Cache::Deliver::";


    /**
     * Caffeine 缓存管理器
     */
    public static final String CAFFEINE_CACHE_MANAGER = "caffeineCacheManager";

    /**
     * Redis 缓存管理器
     */
    public static final String REDIS_CACHE_MANAGER = "redisCacheManager";

    /**
     * 多级缓存管理器
     */
    public static final String COMPOSITE_CACHE_MANAGER = "compositeCacheManager";

    public static final String TEMPLATE_CACHE_NAME = "template";
    public static final String TEMPLATE_APP_CACHE_NAME = "templateApp";
    public static final String APP_CACHE_NAME = "app";
    public static final String CLIENT_TOKEN_CACHE_NAME = "clientToken";

    /**
     * 缓存配置常量
     */
    @Getter
    public enum CacheEnum {

        TEMPLATE(TEMPLATE_CACHE_NAME, 60 * 24, 20),

        TEMPLATE_APP(TEMPLATE_APP_CACHE_NAME, 60 * 24, 50),

        APP(APP_CACHE_NAME, 60 * 24, 50),

        /**
         * Token 缓存时间为 120 分钟，钉钉、企微、飞书的 Token 有效期都为 120 分钟
         */
        CLIENT_TOKEN(CLIENT_TOKEN_CACHE_NAME, 120, 50);

        /**
         * 缓存的名字
         */
        private final String name;
        /**
         * 失效时间（秒） 0-永不失效
         */
        private final long ttl;
        /**
         * 最大容量
         */
        private final int maxSize;

        CacheEnum(String name, int ttl, int maxSize) {
            this.name = name;
            this.ttl = ttl;
            this.maxSize = maxSize;
        }

    }
}
