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

package com.oszero.deliver.business.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oszero.deliver.business.admin.exception.BusinessException;
import com.oszero.deliver.business.admin.mapper.GroupMapper;
import com.oszero.deliver.business.admin.mapper.PeopleGroupMapper;
import com.oszero.deliver.business.admin.mapper.SendTaskMapper;
import com.oszero.deliver.business.admin.mapper.UserGroupMapper;
import com.oszero.deliver.business.admin.model.dto.request.group.CurrentLoginUserGroupDataRequestDto;
import com.oszero.deliver.business.admin.model.dto.request.group.GroupSaveRequestDto;
import com.oszero.deliver.business.admin.model.dto.request.group.GroupTopUpRequestDto;
import com.oszero.deliver.business.admin.model.dto.request.group.GroupUpdateRequestDto;
import com.oszero.deliver.business.admin.model.dto.response.group.CurrentLoginUserGroupDataResponseDto;
import com.oszero.deliver.business.admin.model.entity.database.GroupInfo;
import com.oszero.deliver.business.admin.model.entity.database.PeopleGroup;
import com.oszero.deliver.business.admin.model.entity.database.SendTask;
import com.oszero.deliver.business.admin.model.entity.database.UserGroup;
import com.oszero.deliver.business.admin.service.GroupService;
import com.oszero.deliver.business.admin.util.UserUtils;
import com.oszero.deliver.business.common.mapper.ChannelAppMapper;
import com.oszero.deliver.business.common.mapper.MessageTemplateMapper;
import com.oszero.deliver.business.common.model.entity.ChannelApp;
import com.oszero.deliver.business.common.model.entity.MessageTemplate;
import com.oszero.deliver.business.common.util.DataBaseUtils;
import com.oszero.deliver.business.common.util.DoubleStatusUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author oszero
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class GroupServiceImpl extends ServiceImpl<GroupMapper, GroupInfo>
        implements GroupService {

    private final GroupMapper groupMapper;
    private final UserGroupMapper userGroupMapper;
    private final MessageTemplateMapper messageTemplateMapper;
    private final ChannelAppMapper channelAppMapper;
    private final SendTaskMapper sendTaskMapper;
    private final PeopleGroupMapper peopleGroupMapper;

    @Override
    public CurrentLoginUserGroupDataResponseDto getGroupData(CurrentLoginUserGroupDataRequestDto dto) {
        long currentLoginUserId = UserUtils.getCurrentLoginUserId();
        LambdaQueryWrapper<UserGroup> userGroupLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userGroupLambdaQueryWrapper.eq(UserGroup::getUserId, currentLoginUserId);
        List<UserGroup> userGroups = userGroupMapper.selectList(userGroupLambdaQueryWrapper);
        List<CurrentLoginUserGroupDataResponseDto.GroupItem> topUpGroupList = new ArrayList<>();
        List<CurrentLoginUserGroupDataResponseDto.GroupItem> defaultGroupList = new ArrayList<>();
        if (CollectionUtil.isEmpty(userGroups)) {
            GroupInfo defaultGroupInfo = getDefaultGroup();
            int insert1 = groupMapper.insert(defaultGroupInfo);
            UserGroup userGroup = UserGroup.builder()
                    .groupId(defaultGroupInfo.getGroupId())
                    .userId(currentLoginUserId).build();
            int insert2 = userGroupMapper.insert(userGroup);
            if (DataBaseUtils.isSingleDataModifyFail(insert1) || DataBaseUtils.isSingleDataModifyFail(insert2)) {
                throw new BusinessException("获取分组数据失败");
            }
            defaultGroupList.add(buildGroupItemFromGroup(defaultGroupInfo));
        } else {
            topUpGroupList.addAll(userGroups.stream()
                    .filter(userGroup -> DoubleStatusUtils.enable(userGroup.getTopUp()))
                    .map(userGroup -> {
                        GroupInfo groupInfo = groupMapper.selectById(userGroup.getGroupId());
                        groupInfo.setTopUp(userGroup.getTopUp());
                        return groupInfo;
                    })
                    .sorted((o1, o2) -> o2.getUpdateTime().compareTo(o1.getUpdateTime()))
                    .map(this::buildGroupItemFromGroup)
                    .toList());
            defaultGroupList.addAll(userGroups.stream()
                    .map(userGroup ->  {
                        GroupInfo groupInfo = groupMapper.selectById(userGroup.getGroupId());
                        groupInfo.setTopUp(userGroup.getTopUp());
                        return groupInfo;
                    })
                    .sorted((o1, o2) -> o2.getUpdateTime().compareTo(o1.getUpdateTime()))
                    .map(this::buildGroupItemFromGroup)
                    .toList());
        }
        if (!StrUtil.isEmpty(dto.getGroupName())) {
            String groupName = dto.getGroupName();
            topUpGroupList = topUpGroupList.stream().filter(groupItem -> StrUtil.contains(groupItem.getGroupName(), groupName)).toList();
            defaultGroupList = defaultGroupList.stream().filter(groupItem -> StrUtil.contains(groupItem.getGroupName(), groupName)).toList();
        }
        return CurrentLoginUserGroupDataResponseDto.builder()
                .topUpGroupList(topUpGroupList)
                .defaultGroupList(defaultGroupList).build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteGroup(Long groupId) {
        long association = 0;
        association += messageTemplateMapper.selectCount(new LambdaQueryWrapper<MessageTemplate>().eq(MessageTemplate::getGroupId, groupId));
        association += channelAppMapper.selectCount(new LambdaQueryWrapper<ChannelApp>().eq(ChannelApp::getGroupId, groupId));
        association += sendTaskMapper.selectCount(new LambdaQueryWrapper<SendTask>().eq(SendTask::getGroupId, groupId));
        association += peopleGroupMapper.selectCount(new LambdaQueryWrapper<PeopleGroup>().eq(PeopleGroup::getGroupId, groupId));
        if (association > 0) {
            throw new BusinessException("该分组下存在关联数据，无法删除，请删除关联的消息模板、渠道应用、群发任务、人群后再删除此分组");
        }
        LambdaUpdateWrapper<UserGroup> wrapper = new LambdaUpdateWrapper<UserGroup>()
                .eq(UserGroup::getGroupId, groupId)
                .eq(UserGroup::getUserId, UserUtils.getCurrentLoginUserId());
        int deleteUserGroup = userGroupMapper.delete(wrapper);
        int deleteGroup = groupMapper.deleteById(groupId);
        if (DataBaseUtils.isSingleDataModifyFail(deleteUserGroup) || DataBaseUtils.isSingleDataModifyFail(deleteGroup)) {
            throw new BusinessException("删除分组失败");
        }
    }

    @Override
    public void updateGroup(GroupUpdateRequestDto dto) {
        checkGroupNameIsDuplicate(dto.getGroupId(), dto.getGroupName());
        GroupInfo groupInfo = new GroupInfo();
        BeanUtil.copyProperties(dto, groupInfo);
        int updateById = groupMapper.updateById(groupInfo);
        if (DataBaseUtils.isSingleDataModifyFail(updateById)) {
            throw new BusinessException("更新分组失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveGroup(GroupSaveRequestDto dto) {
        checkGroupNameIsDuplicate(null, dto.getGroupName());
        GroupInfo groupInfo = new GroupInfo();
        BeanUtil.copyProperties(dto, groupInfo);
        int insert1 = groupMapper.insert(groupInfo);
        UserGroup userGroup = UserGroup.builder()
                .userId(UserUtils.getCurrentLoginUserId())
                .groupId(groupInfo.getGroupId())
                .build();
        int insert2 = userGroupMapper.insert(userGroup);
        if (DataBaseUtils.isSingleDataModifyFail(insert1) || DataBaseUtils.isSingleDataModifyFail(insert2)) {
            throw new BusinessException("新增分组失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void topUpGroup(GroupTopUpRequestDto dto) {
        LambdaQueryWrapper<UserGroup> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserGroup::getUserId, UserUtils.getCurrentLoginUserId())
                .eq(UserGroup::getGroupId, dto.getGroupId());
        int update1 = userGroupMapper.update(UserGroup.builder().topUp(dto.getTopUp()).build(), wrapper);
        GroupInfo groupInfo = GroupInfo.builder().groupId(dto.getGroupId()).build();
        groupInfo.setUpdateTime(LocalDateTime.now());
        int update2 = groupMapper.updateById(groupInfo);
        if (DataBaseUtils.isSingleDataModifyFail(update1) || DataBaseUtils.isSingleDataModifyFail(update2)) {
            throw new BusinessException("置顶或取消置顶失败");
        }
    }

    @Override
    public boolean currentLoginUserHasGroup(Long groupId) {
        LambdaQueryWrapper<UserGroup> userGroupLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userGroupLambdaQueryWrapper.eq(UserGroup::getUserId, UserUtils.getCurrentLoginUserId())
                .eq(UserGroup::getGroupId, groupId);
        long count = userGroupMapper.selectCount(userGroupLambdaQueryWrapper);
        return count > 0;
    }

    private CurrentLoginUserGroupDataResponseDto.GroupItem buildGroupItemFromGroup(GroupInfo groupInfo) {
        return CurrentLoginUserGroupDataResponseDto.GroupItem.builder()
                .groupId(groupInfo.getGroupId())
                .groupName(groupInfo.getGroupName())
                .groupDescription(groupInfo.getGroupDescription())
                .topUp(groupInfo.getTopUp())
                .updateTime(groupInfo.getUpdateTime())
                .build();
    }

    private GroupInfo getDefaultGroup() {
        GroupInfo groupInfo = new GroupInfo();
        groupInfo.setGroupName("默认分组");
        groupInfo.setGroupDescription("这是一个默认分组");
        return groupInfo;
    }

    private void checkGroupNameIsDuplicate(Long groupId, String groupName) {
        LambdaQueryWrapper<GroupInfo> wrapper = new LambdaQueryWrapper<>();
        if (Objects.isNull(groupId)) {
            wrapper.eq(GroupInfo::getGroupName, groupName);
            Long count = groupMapper.selectCount(wrapper);
            if (count > 0) {
                throw new BusinessException(String.format("分组名：'%s'已存在，请更换一个", groupName));
            }
        } else {
            wrapper.eq(GroupInfo::getGroupName, groupName)
                    .or().eq(GroupInfo::getGroupId, groupId);
            Long count = groupMapper.selectCount(wrapper);
            if (count > 1) {
                throw new BusinessException(String.format("分组名：'%s'已存在，请更换一个", groupName));
            }
        }
    }
}




