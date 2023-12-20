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

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.*;

/**
 * 平台文件上传请求 dto
 *
 * @author oszero
 * @version 1.0.0
 */
@Data
public class PlatformFileUploadRequestDto {

    /**
     * 文件名
     */
    @NotBlank
    private String fileName;

    /**
     * APP 类型（1-钉钉2-企业微信3-飞书）
     */
    @NotNull
    private Integer appType;

    /**
     * 文件类型
     */
    @NotBlank
    private String fileType;

    /**
     * 关联 AppId
     */
    @NotNull
    private Long appId;

    /**
     * 平台文件
     */
    @NotNull
    private MultipartFile platformFile;
}
