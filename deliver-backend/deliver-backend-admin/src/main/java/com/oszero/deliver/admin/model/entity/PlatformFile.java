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

package com.oszero.deliver.admin.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 平台文件表
 *
 * @author oszero
 * @version 1.0.0
 */
@Data
@TableName(value = "platform_file")
public class PlatformFile implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * APP 类型（1-钉钉2-企业微信3-飞书）
     */
    private Integer appType;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 文件平台 Key
     */
    private String fileKey;

    /**
     * 文件平台状态（1-生效0-失效）
     */
    private Integer fileStatus;

    /**
     * 创建者
     */
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 关联 AppId
     */
    private Long appId;

    private Long groupId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}