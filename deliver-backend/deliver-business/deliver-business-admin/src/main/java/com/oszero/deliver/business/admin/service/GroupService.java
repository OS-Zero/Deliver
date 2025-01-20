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

package com.oszero.deliver.business.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.oszero.deliver.business.admin.model.dto.request.group.CurrentLoginUserGroupDataRequestDto;
import com.oszero.deliver.business.admin.model.dto.request.group.GroupSaveRequestDto;
import com.oszero.deliver.business.admin.model.dto.request.group.GroupTopUpRequestDto;
import com.oszero.deliver.business.admin.model.dto.request.group.GroupUpdateRequestDto;
import com.oszero.deliver.business.admin.model.dto.response.group.CurrentLoginUserGroupDataResponseDto;
import com.oszero.deliver.business.admin.model.entity.database.GroupInfo;
import jakarta.validation.Valid;

/**
 * @author oszero
 * @version 1.0.0
 */
public interface GroupService extends IService<GroupInfo> {
    CurrentLoginUserGroupDataResponseDto getGroupData(CurrentLoginUserGroupDataRequestDto dto);
    void deleteGroup(Long groupId);
    void updateGroup(GroupUpdateRequestDto dto);
    void saveGroup(GroupSaveRequestDto dto);
    void topUpGroup(@Valid GroupTopUpRequestDto dto);
    boolean currentLoginUserHasGroup(Long groupId);
}
