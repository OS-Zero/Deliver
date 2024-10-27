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

package com.oszero.deliver.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oszero.deliver.admin.exception.BusinessException;
import com.oszero.deliver.admin.mapper.AdminAppMapper;
import com.oszero.deliver.admin.mapper.AdminGroupMapper;
import com.oszero.deliver.admin.mapper.AdminTemplateMapper;
import com.oszero.deliver.admin.model.dto.request.GroupSaveAndUpdateRequestDto;
import com.oszero.deliver.admin.model.dto.response.GroupGetAllResponseDto;
import com.oszero.deliver.admin.model.entity.App;
import com.oszero.deliver.admin.model.entity.Group;
import com.oszero.deliver.admin.model.entity.Template;
import com.oszero.deliver.admin.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 模板 serviceImpl
 *
 * @author oszero
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class AdminGroupServiceImpl extends ServiceImpl<AdminGroupMapper, Group>
        implements GroupService {

    private final AdminGroupMapper adminGroupMapper;
    private final AdminTemplateMapper adminTemplateMapper;
    private final AdminAppMapper adminAppMapper;

    @Override
    public List<GroupGetAllResponseDto> getAll() {
        LambdaQueryWrapper<Group> groupLambdaQueryWrapper = new LambdaQueryWrapper<>();
        groupLambdaQueryWrapper.orderByDesc(Group::getCreateTime);
        List<Group> groups = adminGroupMapper.selectList(groupLambdaQueryWrapper);
        return groups.stream().map((group) -> {
            GroupGetAllResponseDto groupGetAllResponseDto = new GroupGetAllResponseDto();
            groupGetAllResponseDto.setGroupId(group.getGroupId());
            groupGetAllResponseDto.setGroupName(group.getGroupName());
            groupGetAllResponseDto.setGroupDescription(group.getGroupDescription());
            groupGetAllResponseDto.setCreateUser(group.getCreateUser());
            groupGetAllResponseDto.setCreateTime(group.getCreateTime());
            return groupGetAllResponseDto;
        }).collect(Collectors.toList());
    }

    @Override
    public void deleteGroup(Long groupId) {
        LambdaQueryWrapper<Template> templateLambdaQueryWrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<App> appLambdaQueryWrapper = new LambdaQueryWrapper<>();
        templateLambdaQueryWrapper.eq(Template::getGroupId, groupId);
        appLambdaQueryWrapper.eq(App::getGroupId, groupId);
        List<Template> templates = adminTemplateMapper.selectList(templateLambdaQueryWrapper);
        List<App> apps = adminAppMapper.selectList(appLambdaQueryWrapper);
        if (!templates.isEmpty() || !apps.isEmpty()) {
            throw new BusinessException("此分组下还有模板和应用未删除，请删除分组下的所有模板和应用再来删除分组");
        }
        int deleted = adminGroupMapper.deleteById(groupId);
        if (deleted != 1) {
            throw new BusinessException("删除分组失败");
        }
    }

    @Override
    public void updateGroup(GroupSaveAndUpdateRequestDto dto) {
        dto.setGroupName(dto.getGroupName().trim());
        dto.setGroupDescription(dto.getGroupDescription().trim());
        checkGroupNameIsDuplicate(dto);
        Group group = new Group();
        BeanUtil.copyProperties(dto, group);
        boolean update = adminGroupMapper.updateById(group) == 1;
        if (!update) {
            throw new BusinessException("编辑分组失败");
        }
    }

    @Override
    public void saveGroup(GroupSaveAndUpdateRequestDto dto) {
        dto.setGroupName(dto.getGroupName().trim());
        dto.setGroupDescription(dto.getGroupDescription().trim());
        checkGroupNameIsDuplicate(dto);
        Group group = new Group();
        BeanUtil.copyProperties(dto, group);
        boolean save = adminGroupMapper.insert(group) == 1;
        if (!save) {
            throw new BusinessException("新增分组失败");
        }
    }

    private void checkGroupNameIsDuplicate(GroupSaveAndUpdateRequestDto dto) {
        Long groupId = dto.getGroupId();
        String groupName = dto.getGroupName();
        LambdaQueryWrapper<Group> wrapper = new LambdaQueryWrapper<>();
        // 为 null 表示新增
        if (Objects.isNull(groupId)) {
            wrapper.eq(Group::getGroupName, groupName);
            Group one = this.getOne(wrapper);
            if (!Objects.isNull(one)) {
                throw new BusinessException("此分组名(" + groupName + ")已存在！！！");
            }
        } else {
            wrapper.eq(Group::getGroupName, groupName)
                    .or().eq(Group::getGroupId, groupId);
            List<Group> list = this.list(wrapper);
            if (list.size() > 1) {
                throw new BusinessException("此分组名(" + groupName + ")已存在！！！");
            }
        }
    }
}




