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

package com.oszero.deliver.server.model.dto.common;

import com.oszero.deliver.server.pretreatment.common.LinkModel;
import lombok.*;

import java.util.List;
import java.util.Map;

/**
 * 发送消息任务 DTO
 *
 * @author oszero
 * @version 1.0.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SendTaskDto extends LinkModel {

    /**
     * 消息模板 Id
     */
    private Long templateId;

    /**
     * appId
     */
    private Long appId;

    /**
     * 发送用户列表
     */
    private List<String> users;

    /**
     * 不同消息的不同参数
     */
    private Map<String, Object> paramMap;

    /**
     * appConfig 数据库的 JSON 数据加密
     */
    private String appConfig;

    /**
     * 发送范围
     */
    private Integer pushRange;

    /**
     * 发送用户类型
     */
    private Integer usersType;

    /**
     * 渠道类型
     */
    private Integer channelType;

    /**
     * 消息类型
     */
    private String messageType;

    /**
     * 消息链路追踪 id
     */
    private String traceId;

    /**
     * 请求客户端 IP
     */
    private String clientIp;

    /**
     * 是否重试消息（1-是 0-首次发送）
     */
    private Integer retried = 0;

    /**
     * 失败重试次数，默认为 0
     */
    private Integer retry = 0;
}


