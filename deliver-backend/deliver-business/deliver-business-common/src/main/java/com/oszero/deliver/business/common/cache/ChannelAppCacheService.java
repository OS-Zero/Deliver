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
import com.oszero.deliver.business.common.mapper.ChannelAppMapper;
import com.oszero.deliver.business.common.model.entity.cache.ChannelAppCache;
import com.oszero.deliver.business.common.model.entity.database.ChannelApp;
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
public class ChannelAppCacheService extends AbstractCacheService<Long, ChannelAppCache> {

    private final ChannelAppMapper channelAppMapper;

    @Override
    public String getCacheName() {
        return CommonCacheConstant.CHANNEL_APP_CACHE_NAME;
    }

    @Override
    public ChannelAppCache queryData(Long key) {
        ChannelApp channelApp = channelAppMapper.selectById(key);
        if (Objects.isNull(channelApp)) {
            return null;
        }
        ChannelAppCache channelAppCache = new ChannelAppCache();
        BeanUtil.copyProperties(channelApp, channelAppCache);
        return channelAppCache;
    }

    @Override
    public Map<Long, ChannelAppCache> queryDataList(List<Long> keyList) {
        return Map.of();
    }
}
