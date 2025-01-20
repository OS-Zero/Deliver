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

package com.oszero.deliver.business.server.pretreatment.common;

import cn.hutool.core.collection.CollUtil;
import com.oszero.deliver.business.server.exception.MessageException;
import com.oszero.deliver.business.server.model.dto.common.SendTaskDto;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author oszero
 * @version 1.0.0
 */
@Data
@Accessors(chain = true)
public class LinkHandler {

    private Map<String, LinkTemplate> templateConfig = null;

    public LinkContext<SendTaskDto> process(LinkContext<SendTaskDto> context) {
        // 前置检查
        preCheck(context);
        // 遍历流程节点
        List<MessageLink<SendTaskDto>> processList = templateConfig.get(context.getCode()).getProcessList();
        processList.forEach(businessLink -> businessLink.process(context));
        return context;
    }

    private void preCheck(LinkContext<SendTaskDto> context) {
        if (Objects.isNull(context)) {
            throw new MessageException("消息前置处理责任链执行上下文为空");
        }
        String businessCode = context.getCode();
        LinkTemplate linkTemplate = templateConfig.get(businessCode);
        if (Objects.isNull(linkTemplate)) {
            throw new MessageException("消息前置处理责任链无法找到执行模板");
        }
        List<MessageLink<SendTaskDto>> processList = linkTemplate.getProcessList();
        if (CollUtil.isEmpty(processList)) {
            throw new MessageException("消息前置处理责任链执行链路为空");
        }
    }
}
