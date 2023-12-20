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

package com.oszero.deliver.server.mq.consumer.common;

import cn.hutool.core.util.StrUtil;
import com.oszero.deliver.server.constant.TraceIdConstant;
import com.oszero.deliver.server.enums.StatusEnum;
import com.oszero.deliver.server.exception.MessageException;
import com.oszero.deliver.server.handler.BaseHandler;
import com.oszero.deliver.server.mq.producer.Producer;
import com.oszero.deliver.server.model.dto.common.SendTaskDto;
import com.oszero.deliver.server.util.MdcUtils;
import com.oszero.deliver.server.util.MessageLinkTraceUtils;
import com.oszero.deliver.server.web.service.MessageRecordService;

import java.util.Objects;

/**
 * 消息队列通用处理
 *
 * @author oszero
 * @version 1.0.0
 */
public class MQCommonConsumer {

    /**
     * try 块处理
     *
     * @param sendTaskDto 发送任务
     * @param handler     渠道处理器
     * @throws Exception 异常
     */
    public static void tryHandle(SendTaskDto sendTaskDto, BaseHandler handler) throws Exception {

        // 记录链路追踪 id
        String traceId = sendTaskDto.getTraceId();
        if (StrUtil.isBlank(traceId)) {
            throw new MessageException(sendTaskDto, "traceId 为空");
        }
        MdcUtils.put(TraceIdConstant.TRACE_ID, traceId);

        MessageLinkTraceUtils.recordMessageLifecycleInfoLog(sendTaskDto, "接收到消息队列消息，消息已送达消费者");

        // 处理器处理
        handler.doHandle(sendTaskDto);
    }

    /**
     * catch 块处理
     *
     * @param sendTaskDto          发送任务
     * @param exception            异常
     * @param messageRecordService 消息记录service
     * @param producer             生产者
     */
    public static void catchHandle(SendTaskDto sendTaskDto, Exception exception, MessageRecordService messageRecordService, Producer producer) {
        if (!Objects.isNull(sendTaskDto)) {
            // 记录错误日志
            MessageLinkTraceUtils.recordMessageLifecycleErrorLog(exception.getMessage());
            // 记录异常信息到生命周期日志中
            MessageLinkTraceUtils.recordMessageLifecycleError2InfoLog(exception.getMessage());
            // 记录消息消费失败
            sendTaskDto.getUsers().forEach(user -> messageRecordService.saveMessageRecord(sendTaskDto, StatusEnum.OFF, user));

            if (sendTaskDto.getRetry() > 0) {
                // 重新发送
                sendTaskDto.setRetry(sendTaskDto.getRetry() - 1);
                sendTaskDto.setRetried(StatusEnum.ON.getStatus());
                producer.sendMessage(sendTaskDto);

                MessageLinkTraceUtils.recordMessageLifecycleInfoLog(sendTaskDto, "重试消息已发送");
            } else {
                // 记录错误日志
                MessageLinkTraceUtils.recordMessageLifecycleErrorLog(sendTaskDto, "消息发送失败，重试次数已用完！！！");
                // 记录异常信息到生命周期日志中
                MessageLinkTraceUtils.recordMessageLifecycleError2InfoLog(sendTaskDto, "消息发送失败，重试次数已用完！！！");
            }
        } else {
            // 记录错误日志
            MessageLinkTraceUtils.recordMessageLifecycleErrorLog("消息消费失败，" + exception.getMessage() + "！！！");
            // 记录异常信息到生命周期日志中
            MessageLinkTraceUtils.recordMessageLifecycleError2InfoLog("消息消费失败，" + exception.getMessage() + "！！！");
        }
        MdcUtils.clear();
    }
}
