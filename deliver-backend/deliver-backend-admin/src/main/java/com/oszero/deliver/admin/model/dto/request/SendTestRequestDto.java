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

package com.oszero.deliver.admin.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.*;

import java.util.List;
import java.util.Map;

/**
 * 发送请求 DTO
 *
 * @author oszero
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendTestRequestDto {

    /**
     * 消息模板 Id
     */
    @NotNull(message = "消息模板 ID 不能为 NULL")
    private Long templateId;

    /**
     * 发送用户列表
     */
    @NotEmpty(message = "发送用户列表不能为空")
    private List<String> users;

    /**
     * 不同消息的不同参数
     */
    @NotEmpty(message = "消息参数不能为空")
    private Map<String, Object> paramMap;

    /**
     * 重试次数
     * 默认为 0
     */
    private Integer retry = 0;
}
