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
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.jesse.l2cache.spring.biz.AbstractCacheService;
import com.oszero.deliver.business.common.constant.CommonCacheConstant;
import com.oszero.deliver.business.common.mapper.TemplateAppMapper;
import com.oszero.deliver.business.common.model.entity.cache.TemplateAppCache;
import com.oszero.deliver.business.common.model.entity.database.TemplateApp;
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
public class TemplateAppCacheService extends AbstractCacheService<Long, TemplateAppCache> {

    private final TemplateAppMapper templateAppMapper;

    @Override
    public String getCacheName() {
        return CommonCacheConstant.TEMPLATE_APP_CACHE_NAME;
    }

    @Override
    public TemplateAppCache queryData(Long key) {
        LambdaQueryWrapper<TemplateApp> templateAppLambdaQueryWrapper = new LambdaQueryWrapper<>();
        templateAppLambdaQueryWrapper.eq(TemplateApp::getTemplateId, key);
        TemplateApp templateApp = templateAppMapper.selectOne(templateAppLambdaQueryWrapper);
        if (Objects.isNull(templateApp)) {
            return null;
        }
        TemplateAppCache templateAppCache = new TemplateAppCache();
        BeanUtil.copyProperties(templateApp, templateAppCache);
        return templateAppCache;
    }

    @Override
    public Map<Long, TemplateAppCache> queryDataList(List<Long> keyList) {
        return Map.of();
    }
}
