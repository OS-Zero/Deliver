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

package com.oszero.deliver.server.pretreatment.common;

import cn.hutool.core.collection.CollUtil;
import com.oszero.deliver.server.exception.MessageException;
import com.oszero.deliver.server.model.dto.common.SendTaskDto;
import com.oszero.deliver.server.util.MessageLinkTraceUtils;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 责任链处理器
 *
 * @author oszero
 * @version 1.0.0
 */
@Slf4j
@Data
@Accessors(chain = true)
public class LinkHandler {

    /**
     * 模板映射
     */
    private Map<String, LinkTemplate> templateConfig = null;

    /**
     * 执行责任链
     *
     * @param context 上下文
     * @return 返回上下文内容
     */
    public LinkContext<SendTaskDto> process(LinkContext<SendTaskDto> context) {

        //1. 前置检查
        preCheck(context);
        SendTaskDto sendTaskDto = context.getProcessModel();
        MessageLinkTraceUtils.recordMessageLifecycleInfoLog(sendTaskDto, "完成责任链配置检查");

        //2. 遍历流程节点
        List<MessageLink<SendTaskDto>> processList = templateConfig.get(context.getCode()).getProcessList();
        processList.forEach(businessLink -> businessLink.process(context));
        return context;
    }

    /**
     * 执行前检查检查参数是否达到要求，出错则抛出异常
     *
     * @param context 执行上下文
     */
    private void preCheck(LinkContext<SendTaskDto> context) {
        SendTaskDto sendTaskDto = new SendTaskDto();
        if (!Objects.isNull(context)) {
            sendTaskDto = context.getProcessModel();
        }

        if (Objects.isNull(context)) {
            throw new MessageException(sendTaskDto, "消息前置处理责任链执行上下文为空");
        }

        String businessCode = context.getCode();

        LinkTemplate linkTemplate = templateConfig.get(businessCode);
        if (Objects.isNull(linkTemplate)) {
            throw new MessageException(sendTaskDto, "消息前置处理责任链无法找到执行模板");
        }

        List<MessageLink<SendTaskDto>> processList = linkTemplate.getProcessList();
        if (CollUtil.isEmpty(processList)) {
            throw new MessageException(sendTaskDto, "消息前置处理责任链执行链路为空");
        }

    }
}
