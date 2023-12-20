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

package com.oszero.deliver.server.cache.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.oszero.deliver.server.cache.constant.CacheConstant;
import com.oszero.deliver.server.client.ding.DingClient;
import com.oszero.deliver.server.client.feishu.FeiShuClient;
import com.oszero.deliver.server.client.wechat.WeChatClient;
import com.oszero.deliver.server.model.app.DingApp;
import com.oszero.deliver.server.model.app.FeiShuApp;
import com.oszero.deliver.server.model.app.WeChatApp;
import com.oszero.deliver.server.model.dto.common.SendTaskDto;
import com.oszero.deliver.server.model.entity.App;
import com.oszero.deliver.server.model.entity.Template;
import com.oszero.deliver.server.model.entity.TemplateApp;
import com.oszero.deliver.server.web.service.AppService;
import com.oszero.deliver.server.web.service.TemplateAppService;
import com.oszero.deliver.server.web.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 服务端各缓存管理器
 *
 * @author oszero
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class ServerCacheManager {

    private final TemplateService templateService;
    private final TemplateAppService templateAppService;
    private final AppService appService;

    private final DingClient dingClient;
    private final WeChatClient weChatClient;
    private final FeiShuClient feiShuClient;

    /**
     * 缓存建立
     */
    @Cacheable(value = CacheConstant.TEMPLATE_CACHE_NAME, key = "#templateId",
            cacheManager = CacheConstant.REDIS_CACHE_MANAGER)
    public Template getTemplate(Long templateId) {
        return templateService.getById(templateId);
    }

    @Cacheable(value = CacheConstant.TEMPLATE_APP_CACHE_NAME, key = "#templateId",
            cacheManager = CacheConstant.REDIS_CACHE_MANAGER)
    public TemplateApp getTemplateApp(Long templateId) {
        LambdaQueryWrapper<TemplateApp> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TemplateApp::getTemplateId, templateId);
        return templateAppService.getOne(wrapper);
    }

    @Cacheable(value = CacheConstant.APP_CACHE_NAME, key = "#appId",
            cacheManager = CacheConstant.REDIS_CACHE_MANAGER)
    public App getApp(Long appId) {
        return appService.getById(appId);
    }

//    @Cacheable(value = CacheConstant.CLIENT_TOKEN_CACHE_NAME, keyGenerator = "tokenKeyGenerator",
//            cacheManager = CacheConstant.REDIS_CACHE_MANAGER)
    public String getDingToken(DingApp dingApp, SendTaskDto sendTaskDto) {
        return dingClient.getAccessToken(dingApp, sendTaskDto);
    }

//    @Cacheable(value = CacheConstant.CLIENT_TOKEN_CACHE_NAME, keyGenerator = "tokenKeyGenerator",
//            cacheManager = CacheConstant.REDIS_CACHE_MANAGER)
    public String getWeChatToken(WeChatApp weChatApp, SendTaskDto sendTaskDto) {
        return weChatClient.getAccessToken(weChatApp, sendTaskDto);
    }

//    @Cacheable(value = CacheConstant.CLIENT_TOKEN_CACHE_NAME, keyGenerator = "tokenKeyGenerator",
//            cacheManager = CacheConstant.REDIS_CACHE_MANAGER)
    public String getFeiShuToken(FeiShuApp feiShuApp, SendTaskDto sendTaskDto) {
        return feiShuClient.getTenantAccessToken(feiShuApp, sendTaskDto);
    }
}
