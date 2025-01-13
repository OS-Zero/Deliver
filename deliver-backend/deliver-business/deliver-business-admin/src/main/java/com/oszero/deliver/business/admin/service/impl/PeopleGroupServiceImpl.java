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
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oszero.deliver.business.admin.mapper.PeopleGroupMapper;
import com.oszero.deliver.business.admin.mapper.SendTaskMapper;
import com.oszero.deliver.business.admin.model.dto.request.common.DeleteIdsRequestDto;
import com.oszero.deliver.business.admin.model.dto.request.peoplegroup.PeopleGroupSaveRequestDto;
import com.oszero.deliver.business.admin.model.dto.request.peoplegroup.PeopleGroupSearchByNameRequestDto;
import com.oszero.deliver.business.admin.model.dto.request.peoplegroup.PeopleGroupSearchRequestDto;
import com.oszero.deliver.business.admin.model.dto.request.peoplegroup.PeopleGroupUpdateRequestDto;
import com.oszero.deliver.business.admin.model.dto.response.common.SearchResponseDto;
import com.oszero.deliver.business.admin.model.dto.response.peoplegroup.PeopleGroupSearchByNameResponseDto;
import com.oszero.deliver.business.admin.model.dto.response.peoplegroup.PeopleGroupSearchResponseDto;
import com.oszero.deliver.business.admin.model.entity.database.PeopleGroup;
import com.oszero.deliver.business.admin.model.entity.database.SendTask;
import com.oszero.deliver.business.admin.model.excel.PeopleListItem;
import com.oszero.deliver.business.admin.service.PeopleGroupService;
import com.oszero.deliver.business.admin.util.GroupUtils;
import com.oszero.deliver.business.admin.util.WebUtils;
import com.oszero.deliver.business.common.enums.UsersTypeEnum;
import com.oszero.deliver.business.admin.exception.BusinessException;
import com.oszero.deliver.business.common.model.entity.MessageTemplate;
import com.oszero.deliver.business.common.util.DataBaseUtils;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author oszero
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class PeopleGroupServiceImpl extends ServiceImpl<PeopleGroupMapper, PeopleGroup>
        implements PeopleGroupService {

    private final PeopleGroupMapper peopleGroupMapper;
    private final SendTaskMapper sendTaskMapper;

    @Override
    public SearchResponseDto<PeopleGroupSearchResponseDto> search(PeopleGroupSearchRequestDto dto) {
        LambdaQueryWrapper<PeopleGroup> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(dto.getPeopleGroupName()), PeopleGroup::getPeopleGroupName, dto.getPeopleGroupName())
                .eq(!Objects.isNull(dto.getUsersType()), PeopleGroup::getUsersType, dto.getUsersType())
                .eq(PeopleGroup::getGroupId, GroupUtils.getGroupId())
                .gt(!Objects.isNull(dto.getStartTime()), PeopleGroup::getCreateTime, dto.getStartTime())
                .lt(!Objects.isNull(dto.getEndTime()), PeopleGroup::getCreateTime, dto.getEndTime())
                .orderByDesc(PeopleGroup::getCreateTime);
        Page<PeopleGroup> peopleGroupPage = new Page<>(dto.getCurrentPage(), dto.getPageSize());
        peopleGroupMapper.selectPage(peopleGroupPage, wrapper);
        List<PeopleGroupSearchResponseDto> list = peopleGroupPage.getRecords().stream()
                .map(group -> {
                    PeopleGroupSearchResponseDto responseDto = new PeopleGroupSearchResponseDto();
                    BeanUtil.copyProperties(group, responseDto);
                    UsersTypeEnum usersTypeEnum = UsersTypeEnum.getInstanceByCode(group.getUsersType());
                    if (Objects.isNull(usersTypeEnum)) {
                        throw new BusinessException("人群类型异常");
                    }
                    responseDto.setUsersTypeName(usersTypeEnum.getName());
                    return responseDto;
                }).toList();
        return SearchResponseDto.<PeopleGroupSearchResponseDto>builder()
                .records(list)
                .total(peopleGroupPage.getTotal())
                .current(peopleGroupPage.getCurrent())
                .size(peopleGroupPage.getSize())
                .pages(peopleGroupPage.getPages())
                .build();
    }

    @Override
    public void save(PeopleGroupSaveRequestDto dto) {
        checkNameIsDuplicate(null, dto.getPeopleGroupName());
        PeopleGroup peopleGroup = new PeopleGroup();
        BeanUtil.copyProperties(dto, peopleGroup);
        peopleGroup.setGroupId(GroupUtils.getGroupId());
        int insert = peopleGroupMapper.insert(peopleGroup);
        if (DataBaseUtils.isSingleDataModifyFail(insert)) {
            throw new BusinessException("人群保存失败");
        }
    }

    @Override
    public void update(PeopleGroupUpdateRequestDto dto) {
        checkNameIsDuplicate(dto.getPeopleGroupId(), dto.getPeopleGroupName());
        PeopleGroup peopleGroup = new PeopleGroup();
        BeanUtil.copyProperties(dto, peopleGroup);
        int update = peopleGroupMapper.updateById(peopleGroup);
        if (DataBaseUtils.isSingleDataModifyFail(update)) {
            throw new BusinessException("人群更新失败");
        }
    }

    @Override
    public void delete(DeleteIdsRequestDto dto) {
        StringBuilder errorMessage = new StringBuilder();
        List<Long> ids = dto.getIds();
        boolean isBind = false;
        for (Long peopleGroupId : ids) {
            LambdaQueryWrapper<SendTask> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SendTask::getPeopleGroupId, peopleGroupId);
            List<SendTask> list = sendTaskMapper.selectList(wrapper);
            errorMessage.append("此人群：").append(peopleGroupId).append("还绑定着以下群发任务：");
            for (SendTask sendTask : list) {
                isBind = true;
                errorMessage.append(sendTask.getTaskId()).append("、");
            }
        }
        if (isBind) {
            errorMessage.delete(errorMessage.length() - 1, errorMessage.length());
            errorMessage.append("，请先解除绑定");
            throw new BusinessException(errorMessage.toString());
        }
        int deleted = peopleGroupMapper.deleteBatchIds(ids);
        if (DataBaseUtils.isBatchDataModifyFail(deleted, ids.size())) {
            throw new BusinessException("删除人群失败");
        }
    }

    @Override
    public void getExcelTemplateFile() {
        String filePath = System.getProperty("user.dir") + "/file/excelTemplateFile.xlsx";
        File templateFile = new File(filePath);
        if (!templateFile.exists()) {
            throw new BusinessException("模板文件不存在");
        }
        HttpServletResponse response = WebUtils.getResponse();
        if (Objects.isNull(response)) {
            throw new BusinessException("响应对象为空");
        }
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=" + templateFile.getName());
        try (FileInputStream fileInputStream = new FileInputStream(templateFile);
             ServletOutputStream outputStream = response.getOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
        } catch (IOException e) {
            throw new BusinessException("文件读取失败");
        }
    }

    @Override
    public String analysisExcelTemplateFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        if (StrUtil.isBlank(fileName) || !(fileName.endsWith(".xlsx") || fileName.endsWith(".xls"))) {
            throw new BusinessException("文件格式错误");
        }
        List<String> data = new ArrayList<>();
        EasyExcel.read(file.getInputStream(), PeopleListItem.class, new PageReadListener<PeopleListItem>(dataList -> {
            for (PeopleListItem peopleListItem : dataList) {
                data.add(peopleListItem.getUserItem());
            }
        }, 100)).sheet().doRead();
        return String.join(",", data);
    }

    @Override
    public List<PeopleGroupSearchByNameResponseDto> searchByName(PeopleGroupSearchByNameRequestDto dto) {
        LambdaQueryWrapper<PeopleGroup> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PeopleGroup::getGroupId, GroupUtils.getGroupId())
                .eq(PeopleGroup::getDeleted, 0)
                .like(PeopleGroup::getPeopleGroupName, dto.getPeopleGroupName())
                .orderByDesc(PeopleGroup::getCreateTime);
        List<PeopleGroup> list = peopleGroupMapper.selectList(wrapper);
        if (CollUtil.isNotEmpty(list)) {
            return list.stream().map(peopleGroup -> PeopleGroupSearchByNameResponseDto.builder()
                    .peopleGroupId(peopleGroup.getPeopleGroupId())
                    .peopleGroupName(peopleGroup.getPeopleGroupName()).build()).toList();
        }
        return List.of();
    }

    private void checkNameIsDuplicate(Long peopleGroupId, String peopleGroupName) {
        LambdaQueryWrapper<PeopleGroup> wrapper = new LambdaQueryWrapper<>();
        if (Objects.isNull(peopleGroupId)) {
            wrapper.eq(PeopleGroup::getPeopleGroupName, peopleGroupName);
            Long count = peopleGroupMapper.selectCount(wrapper);
            if (count > 0) {
                throw new BusinessException(String.format("人群名：'%s'已存在，请更换一个", peopleGroupName));
            }
        } else {
            wrapper.eq(PeopleGroup::getPeopleGroupName, peopleGroupName).or().eq(PeopleGroup::getPeopleGroupId, peopleGroupId);
            Long count = peopleGroupMapper.selectCount(wrapper);
            if (count > 1) {
                throw new BusinessException(String.format("人群名：'%s'已存在，请更换一个", peopleGroupName));
            }
        }
    }
}




