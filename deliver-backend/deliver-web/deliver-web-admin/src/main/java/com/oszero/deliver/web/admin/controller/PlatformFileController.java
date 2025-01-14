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

package com.oszero.deliver.web.admin.controller;

import com.oszero.deliver.business.admin.model.dto.request.platformfile.PlatformFileSearchRequestDto;
import com.oszero.deliver.business.admin.model.dto.request.platformfile.PlatformFileUploadRequestDto;
import com.oszero.deliver.business.admin.model.dto.response.common.SearchResponseDto;
import com.oszero.deliver.business.admin.model.dto.response.platformfile.PlatformFileSearchResponseDto;
import com.oszero.deliver.business.admin.service.PlatformFileService;
import com.oszero.deliver.business.common.model.common.CommonResult;
import com.oszero.deliver.web.admin.constant.AdminPathConstant;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author oszero
 * @version 1.0.0
 */
@Validated
@RestController
@RequiredArgsConstructor
public class PlatformFileController {

    private final PlatformFileService platformFileService;

    @PostMapping(AdminPathConstant.PLATFORM_FILE_SEARCH)
    public CommonResult<SearchResponseDto<PlatformFileSearchResponseDto>> search(@RequestBody PlatformFileSearchRequestDto dto) {
        return CommonResult.success(platformFileService.search(dto));
    }

    @PostMapping(AdminPathConstant.PLATFORM_FILE_UPLOAD_FILE)
    public CommonResult<Void> uploadFile(@Valid PlatformFileUploadRequestDto dto) throws Exception {
        platformFileService.uploadFile(dto);
        return CommonResult.success();
    }
}
