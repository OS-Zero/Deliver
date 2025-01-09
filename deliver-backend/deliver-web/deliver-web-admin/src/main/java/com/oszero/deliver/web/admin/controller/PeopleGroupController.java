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

import com.oszero.deliver.business.admin.model.dto.request.common.DeleteIdsRequestDto;
import com.oszero.deliver.business.admin.model.dto.request.peoplegroup.PeopleGroupSaveRequestDto;
import com.oszero.deliver.business.admin.model.dto.request.peoplegroup.PeopleGroupSearchRequestDto;
import com.oszero.deliver.business.admin.model.dto.request.peoplegroup.PeopleGroupUpdateRequestDto;
import com.oszero.deliver.business.admin.model.dto.response.common.SearchResponseDto;
import com.oszero.deliver.business.admin.model.dto.response.peoplegroup.PeopleGroupSearchResponseDto;
import com.oszero.deliver.business.admin.service.PeopleGroupService;
import com.oszero.deliver.business.common.model.common.CommonResult;
import com.oszero.deliver.web.admin.constant.AdminPathConstant;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author oszero
 * @version 1.0.0
 */
@RestController
@RequiredArgsConstructor
public class PeopleGroupController {
    private final PeopleGroupService peopleGroupService;

    @PostMapping(AdminPathConstant.PEOPLE_GROUP_SEARCH)
    public CommonResult<SearchResponseDto<PeopleGroupSearchResponseDto>> search(@RequestBody PeopleGroupSearchRequestDto dto) {
        return CommonResult.success(peopleGroupService.search(dto));
    }

    @PostMapping(AdminPathConstant.PEOPLE_GROUP_SAVE)
    public CommonResult<Void> save(@Valid @RequestBody PeopleGroupSaveRequestDto dto) {
        peopleGroupService.save(dto);
        return CommonResult.success();
    }

    @PostMapping(AdminPathConstant.PEOPLE_GROUP_UPDATE)
    public CommonResult<Void> update(@Valid @RequestBody PeopleGroupUpdateRequestDto dto) {
        peopleGroupService.update(dto);
        return CommonResult.success();
    }

    @PostMapping(AdminPathConstant.PEOPLE_GROUP_DELETE)
    public CommonResult<Void> delete(@Valid @RequestBody DeleteIdsRequestDto dto) {
        peopleGroupService.delete(dto);
        return CommonResult.success();
    }

    @PostMapping(AdminPathConstant.PEOPLE_GROUP_GET_EXCEL_TEMPLATE_FILE)
    public void getExcelTemplateFile() {
        peopleGroupService.getExcelTemplateFile();
    }

    @PostMapping(AdminPathConstant.PEOPLE_GROUP_ANALYSIS_EXCEL_TEMPLATE_FILE)
    public CommonResult<String> analysisExcelTemplateFile(@RequestParam("file") MultipartFile file) throws IOException {
        return CommonResult.success(peopleGroupService.analysisExcelTemplateFile(file));
    }
}
