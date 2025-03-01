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

package com.oszero.deliver.business.common.constant;

/**
 * @author oszero
 * @version 1.0.0
 */
public interface CommonCacheConstant {
    String SPLIT = "::";
    String REDIS_CACHE_PREFIX = "Cache" + SPLIT + "Deliver" + SPLIT;

    /********** 消息模板缓存常量 **********/
    String MESSAGE_TEMPLATE_CACHE_NAME = REDIS_CACHE_PREFIX + "MessageTemplate" + SPLIT;
    /********** 渠道应用缓存常量 **********/
    String CHANNEL_APP_CACHE_NAME = REDIS_CACHE_PREFIX + "ChannelApp" + SPLIT;
    /********** 模板与应用关联缓存常量 **********/
    String TEMPLATE_APP_CACHE_NAME = REDIS_CACHE_PREFIX + "TemplateApp" + SPLIT;
}
