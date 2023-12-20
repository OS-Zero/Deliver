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
     *
     * @param templateId 模板id
     */
    public void evictTemplate(Long templateId) {
        adminRedisUtils.deleteByKey(CacheConstant.REDIS_CACHE_PREFIX
                + CacheConstant.TEMPLATE_CACHE_NAME
                + "::" + templateId);
    }

    /**
     * 清空模板应用缓存
     *
     * @param templateId 模板id
     */
    public void evictTemplateApp(Long templateId) {
        adminRedisUtils.deleteByKey(CacheConstant.REDIS_CACHE_PREFIX
                + CacheConstant.TEMPLATE_APP_CACHE_NAME
                + "::" + templateId);
    }

    /**
     * 清空应用缓存
     *
     * @param appId 应用id
     */
    public void evictApp(Long appId) {
        adminRedisUtils.deleteByKey(CacheConstant.REDIS_CACHE_PREFIX
                + CacheConstant.APP_CACHE_NAME
                + "::" + appId);
    }
}
