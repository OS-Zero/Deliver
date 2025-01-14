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
import com.oszero.deliver.business.admin.model.dto.request.group.CurrentLoginUserGroupDataRequestDto;
import com.oszero.deliver.business.admin.model.dto.request.group.GroupSaveRequestDto;
import com.oszero.deliver.business.admin.model.dto.request.group.GroupTopUpRequestDto;
import com.oszero.deliver.business.admin.model.dto.request.group.GroupUpdateRequestDto;
import com.oszero.deliver.business.admin.model.dto.response.group.CurrentLoginUserGroupDataResponseDto;
import com.oszero.deliver.business.admin.service.GroupService;
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
public class GroupController {
    private final GroupService groupService;

    @PostMapping(AdminPathConstant.GROUP_GET_GROUP_DATA)
    CommonResult<CurrentLoginUserGroupDataResponseDto> getGroupData(@RequestBody CurrentLoginUserGroupDataRequestDto dto) {
        return CommonResult.success(groupService.getGroupData(dto));
    }

    @PostMapping(AdminPathConstant.GROUP_SAVE)
    CommonResult<Void> saveGroup(@Valid @RequestBody GroupSaveRequestDto dto) {
        groupService.saveGroup(dto);
        return CommonResult.success();
    }

    @PostMapping(AdminPathConstant.GROUP_UPDATE)
    CommonResult<Void> updateGroup(@Valid @RequestBody GroupUpdateRequestDto dto) {
        groupService.updateGroup(dto);
        return CommonResult.success();
    }

    @PostMapping(AdminPathConstant.GROUP_DELETE)
    CommonResult<Void> deleteGroup(@Valid @RequestBody DeleteIdsRequestDto dto) {
        groupService.deleteGroup(dto.getIds().get(0));
        return CommonResult.success();
    }

    @PostMapping(AdminPathConstant.GROUP_TOP_UP)
    CommonResult<Void> topUpGroup(@Valid @RequestBody GroupTopUpRequestDto dto) {
        groupService.topUpGroup(dto);
        return CommonResult.success();
    }
}
