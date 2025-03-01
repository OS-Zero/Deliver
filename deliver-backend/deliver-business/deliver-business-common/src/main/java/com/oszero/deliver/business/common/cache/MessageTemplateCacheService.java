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

package com.oszero.deliver.business.common.cache;

import cn.hutool.core.bean.BeanUtil;
import com.github.jesse.l2cache.spring.biz.AbstractCacheService;
import com.oszero.deliver.business.common.constant.CommonCacheConstant;
import com.oszero.deliver.business.common.mapper.MessageTemplateMapper;
import com.oszero.deliver.business.common.model.entity.cache.MessageTemplateCache;
import com.oszero.deliver.business.common.model.entity.database.MessageTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author oszero
 * @version 1.0.2
 */
@Service
@RequiredArgsConstructor
public class MessageTemplateCacheService extends AbstractCacheService<Long, MessageTemplateCache> {

    private final MessageTemplateMapper messageTemplateMapper;

    @Override
    public String getCacheName() {
        return CommonCacheConstant.MESSAGE_TEMPLATE_CACHE_NAME;
    }

    @Override
    public MessageTemplateCache queryData(Long key) {
        MessageTemplate messageTemplate = messageTemplateMapper.selectById(key);
        if (Objects.isNull(messageTemplate)) {
            return null;
        }
        MessageTemplateCache messageTemplateCache = new MessageTemplateCache();
        BeanUtil.copyProperties(messageTemplate, messageTemplateCache);
        return messageTemplateCache;
    }

    @Override
    public Map<Long, MessageTemplateCache> queryDataList(List<Long> keyList) {
        return Map.of();
    }
}
