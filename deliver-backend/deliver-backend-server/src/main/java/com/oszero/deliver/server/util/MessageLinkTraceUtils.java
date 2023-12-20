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

package com.oszero.deliver.server.util;

import com.oszero.deliver.server.log.MessageLinkTraceLogger;
import com.oszero.deliver.server.model.dto.common.SendTaskDto;

/**
 * MessageLinkTrace 工具类
 *
 * @author oszero
 * @version 1.0.0
 */
public class MessageLinkTraceUtils {

    /**
     * 记录消息生命周期Info日志
     *
     * @param sendTaskDto 发送任务
     * @param msg         信息
     */
    public static void recordMessageLifecycleInfoLog(SendTaskDto sendTaskDto, String msg) {
        MessageLinkTraceLogger.info("消息状态: 正常, 消息链路 ID: {}, 模板 ID: {}, 应用 ID: {}, 接收人列表: {}, 是否重试消息: {}, 重试次数剩余: {}, 请求 IP: {}, 处理信息: {}"
                , sendTaskDto.getTraceId()
                , sendTaskDto.getTemplateId()
                , sendTaskDto.getAppId()
                , sendTaskDto.getUsers()
                , sendTaskDto.getRetried() == 1
                , sendTaskDto.getRetry()
                , sendTaskDto.getClientIp()
                , msg);
    }

    /**
     * 记录消息生命周期Info日志
     *
     * @param msg 信息
     */
    public static void recordMessageLifecycleInfoLog(String msg) {
        MessageLinkTraceLogger.info(msg);
    }

    /**
     * 记录消息生命周期错误日志
     *
     * @param sendTaskDto 发送任务
     * @param msg         信息
     */
    public static void recordMessageLifecycleErrorLog(SendTaskDto sendTaskDto, String msg) {
        MessageLinkTraceLogger.error("消息状态: 异常, 消息链路 ID: {}, 模板 ID: {}, 应用 ID: {}, 接收人列表: {}, 是否重试消息: {}, 重试次数剩余: {}, 请求 IP: {}, 处理信息: {}"
                , sendTaskDto.getTraceId()
                , sendTaskDto.getTemplateId()
                , sendTaskDto.getAppId()
                , sendTaskDto.getUsers()
                , sendTaskDto.getRetried() == 1
                , sendTaskDto.getRetry()
                , sendTaskDto.getClientIp()
                , msg);
    }

    /**
     * 记录消息生命周期错误日志
     *
     * @param msg 信息
     */
    public static void recordMessageLifecycleErrorLog(String msg) {
        MessageLinkTraceLogger.error(msg);
    }

    /**
     * 记录消息生命周期错误信息到Info日志
     *
     * @param sendTaskDto 发送任务
     * @param msg         信息
     */
    public static void recordMessageLifecycleError2InfoLog(SendTaskDto sendTaskDto, String msg) {
        recordMessageLifecycleInfoLog(formatMessageLifecycleErrorLogMsg(sendTaskDto, msg));
    }

    /**
     * 记录消息生命周期错误信息到Info日志
     *
     * @param msg 信息
     */
    public static void recordMessageLifecycleError2InfoLog(String msg) {
        MessageLinkTraceLogger.info(msg);
    }

    /**
     * 格式化消息生命周期错误信息日志
     *
     * @param sendTaskDto 发送任务
     * @param msg         消息
     * @return 日志信息
     */
    public static String formatMessageLifecycleErrorLogMsg(SendTaskDto sendTaskDto, String msg) {
        return String.format("消息状态: 异常, 消息链路 ID: %s, 模板 ID: %s, 应用 ID: %s, 接收人列表: %s, 是否重试消息: %s, 重试次数剩余: %s, 请求 IP: %s, 处理信息: %s"
                , sendTaskDto.getTraceId()
                , sendTaskDto.getTemplateId()
                , sendTaskDto.getAppId()
                , sendTaskDto.getUsers()
                , sendTaskDto.getRetried() == 1
                , sendTaskDto.getRetry()
                , sendTaskDto.getClientIp()
                , msg);
    }

    /**
     * 同时记录错误日志与Info日志
     *
     * @param sendTaskDto 发送任务
     * @param msg         信息
     */
    public static void recordErrorLogAndError2InfoLog(SendTaskDto sendTaskDto, String msg) {
        String errorLogMsg = formatMessageLifecycleErrorLogMsg(sendTaskDto, msg);
        recordMessageLifecycleError2InfoLog(errorLogMsg);
        recordMessageLifecycleErrorLog(errorLogMsg);
    }
}
