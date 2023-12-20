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

package com.oszero.deliver.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oszero.deliver.admin.model.common.CommonResult;
import com.oszero.deliver.admin.model.dto.request.PlatformFileSearchRequestDto;
import com.oszero.deliver.admin.model.dto.request.PlatformFileUploadRequestDto;
import com.oszero.deliver.admin.model.dto.response.PlatformFileSearchResponseDto;
import com.oszero.deliver.admin.service.PlatformFileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 平台文件控制器
 *
 * @author oszero
 * @version 1.0.0
 */
@Validated
@RestController
@RequestMapping("/platformFile")
@RequiredArgsConstructor
public class PlatformFileController {

    private final PlatformFileService platformFileService;

    /**
     * 分页查询平台文件
     *
     * @param dto 查询参数
     * @return Page
     */
    @PostMapping("/getPagePlatformFile")
    public CommonResult<Page<PlatformFileSearchResponseDto>> getPagePlatformFile(@RequestBody PlatformFileSearchRequestDto dto) {
        Page<PlatformFileSearchResponseDto> platformFileSearchResponseDtoPage = platformFileService.getPagePlatformFile(dto);
        return CommonResult.success(platformFileSearchResponseDtoPage);
    }

    /**
     * 上传平台文件
     *
     * @param dto 参数
     * @return 成功
     * @throws Exception 异常
     */
    @PostMapping("/uploadFile")
    public CommonResult<?> uploadFile(@Valid PlatformFileUploadRequestDto dto) throws Exception {
        platformFileService.uploadFile(dto);
        return CommonResult.success();
    }
}
