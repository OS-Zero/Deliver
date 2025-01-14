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

package com.oszero.deliver.business.server.model.dto.common;

import com.oszero.deliver.business.server.pretreatment.common.LinkModel;
import lombok.*;

import java.util.List;
import java.util.Map;

/**
 * @author oszero
 * @version 1.0.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SendTaskDto extends LinkModel {
    private String traceId;
    private Long templateId;
    private Long appId;
    private Long groupId;
    private List<String> users;
    private Map<String, Object> messageParam;
    private String appConfig;
    private Integer usersType;
    private Integer channelType;
    private Integer channelProviderType;
    private String messageType;
    private String clientIp;
    private Integer sendStatus = 0;
    private Integer retried = 0;
    private Integer retryCount = 0;
}


