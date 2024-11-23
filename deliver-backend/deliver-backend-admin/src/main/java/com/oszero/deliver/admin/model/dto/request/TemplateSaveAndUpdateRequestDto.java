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

import com.oszero.deliver.admin.annotation.JsonString;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 模板保存以及更新请求 DTO
 *
 * @author oszero
 * @version 1.0.0
 */
@Data
public class TemplateSaveAndUpdateRequestDto {

    /**
     * 模板id
     */
    private Long templateId;

    /**
     * 模板名称
     */
    @NotBlank
    @Length(min = 3, max = 20)
    private String templateName;

    /**
     * 推送范围（0-不限 1-企业内部 2-外部）
     */
    @NotNull
    private Integer pushRange;

    /**
     * 用户类型（1-企业账号 2-电话 3-平台ID 4-邮箱）
     */
    @NotNull
    private Integer usersType;

    /**
     * 推送方式
     * {
     * "channelType":（1-打电话 2-发短信 3-邮件 4-钉钉 5-企业微信 6-飞书）
     * "messageType": 所见 MessageTypeEnum
     * }
     */
    @NotBlank
    @JsonString
    private String pushWays;

    private Long groupId;

    /**
     * 模板状态
     */
    @NotNull
    private Integer templateStatus;

    /**
     * appId
     */
    @NotNull
    private Long appId;
}
