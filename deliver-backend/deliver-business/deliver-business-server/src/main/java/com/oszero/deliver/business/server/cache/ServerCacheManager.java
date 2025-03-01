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

package com.oszero.deliver.business.server.cache;

import com.oszero.deliver.business.common.cache.ChannelAppCacheService;
import com.oszero.deliver.business.common.cache.MessageTemplateCacheService;
import com.oszero.deliver.business.common.cache.TemplateAppCacheService;
import com.oszero.deliver.business.common.model.entity.cache.ChannelAppCache;
import com.oszero.deliver.business.common.model.entity.cache.MessageTemplateCache;
import com.oszero.deliver.business.common.model.entity.cache.TemplateAppCache;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author oszero
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class ServerCacheManager {
    private final MessageTemplateCacheService messageTemplateCacheService;
    private final ChannelAppCacheService channelAppCacheService;
    private final TemplateAppCacheService templateAppCacheService;

    public MessageTemplateCache getMessageTemplate(Long templateId) {
        return messageTemplateCacheService.getOrLoad(templateId);
    }

    public ChannelAppCache getChannelApp(Long channelAppId) {
        return channelAppCacheService.getOrLoad(channelAppId);
    }

    public TemplateAppCache getTemplateApp(Long templateAppId) {
        return templateAppCacheService.getOrLoad(templateAppId);
    }
}
