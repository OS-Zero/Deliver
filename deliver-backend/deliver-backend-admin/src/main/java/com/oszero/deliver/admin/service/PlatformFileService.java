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

package com.oszero.deliver.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oszero.deliver.admin.model.dto.request.PlatformFileSearchRequestDto;
import com.oszero.deliver.admin.model.dto.request.PlatformFileUploadRequestDto;
import com.oszero.deliver.admin.model.dto.response.PlatformFileSearchResponseDto;
import com.oszero.deliver.admin.model.entity.PlatformFile;

import java.io.IOException;

/**
 * 针对表【platform_file(平台文件表)】的数据库操作Service
 *
 * @author oszero
 * @version 1.0.0
 */
public interface PlatformFileService extends IService<PlatformFile> {

    /**
     * 上传平台文件
     *
     * @param dto 参数
     * @throws IOException 异常
     */
    void uploadFile(PlatformFileUploadRequestDto dto) throws IOException;

    /**
     * 分页搜索平台文件
     *
     * @param dto 参数
     * @return Page
     */
    Page<PlatformFileSearchResponseDto> getPagePlatformFile(PlatformFileSearchRequestDto dto);
}
