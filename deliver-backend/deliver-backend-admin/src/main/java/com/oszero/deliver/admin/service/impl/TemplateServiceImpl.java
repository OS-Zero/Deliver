package com.oszero.deliver.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oszero.deliver.admin.exception.BusinessException;
import com.oszero.deliver.admin.model.dto.request.DeleteIdsRequestDto;
import com.oszero.deliver.admin.model.dto.request.TemplateSaveAndUpdateRequestDto;
import com.oszero.deliver.admin.model.dto.request.TemplateSearchRequestDto;
import com.oszero.deliver.admin.model.dto.response.TemplateSearchResponseDto;
import com.oszero.deliver.admin.model.entity.App;
import com.oszero.deliver.admin.model.entity.Template;
import com.oszero.deliver.admin.model.entity.TemplateApp;
import com.oszero.deliver.admin.service.AppService;
import com.oszero.deliver.admin.service.TemplateAppService;
import com.oszero.deliver.admin.service.TemplateService;
import com.oszero.deliver.admin.mapper.TemplateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class TemplateServiceImpl extends ServiceImpl<TemplateMapper, Template>
        implements TemplateService {

    private final AppService appService;
    private final TemplateAppService templateAppService;

    @Override
    public Page<TemplateSearchResponseDto> search(TemplateSearchRequestDto dto) {
        Page<Template> templatePage =
                new Page<>(dto.getCurrentPage(), dto.getPageSize());
        LambdaQueryWrapper<Template> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(dto.getTemplateName()), Template::getTemplateName, dto.getTemplateName())
                .eq(!Objects.isNull(dto.getPushRange()), Template::getPushRange, dto.getPushRange())
                .eq(!Objects.isNull(dto.getUsersType()), Template::getUsersType, dto.getUsersType())
                .eq(!Objects.isNull(dto.getTemplateStatus()), Template::getTemplateStatus, dto.getTemplateStatus())
                .gt(!Objects.isNull(dto.getStartTime()), Template::getCreateTime, dto.getStartTime())
                .lt(!Objects.isNull(dto.getEndTime()), Template::getCreateTime, dto.getEndTime())
                .orderBy(true, false, Template::getCreateTime);

        this.page(templatePage, wrapper);
        List<TemplateSearchResponseDto> collect = templatePage.getRecords().stream().map(template -> {
            LambdaQueryWrapper<TemplateApp> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(TemplateApp::getTemplateId, template.getTemplateId());
            TemplateApp one = templateAppService.getOne(queryWrapper);
            App app = appService.getById(one.getAppId());

            TemplateSearchResponseDto templateSearchResponseDto = new TemplateSearchResponseDto();
            templateSearchResponseDto.setAppId(app.getAppId());
            templateSearchResponseDto.setAppName(app.getAppName());
            BeanUtil.copyProperties(template, templateSearchResponseDto);
            return templateSearchResponseDto;
        }).collect(Collectors.toList());

        Page<TemplateSearchResponseDto> pageResp = new Page<>(templatePage.getPages(), templatePage.getSize());
        pageResp.setRecords(collect);
        pageResp.setTotal(templatePage.getTotal());
        return pageResp;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByIds(DeleteIdsRequestDto dto) {
        boolean b = this.removeBatchByIds(dto.getIds());
        if (!b) {
            throw new BusinessException("批量删除模板失败！！！");
        }
        dto.getIds().forEach(templateId -> {
            LambdaQueryWrapper<TemplateApp> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(TemplateApp::getTemplateId, templateId);
            boolean remove = templateAppService.remove(wrapper);
            if (!remove) {
                throw new BusinessException("批量删除模板失败！！！");
            }
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateById(TemplateSaveAndUpdateRequestDto dto) {
        Template template = new Template();
        BeanUtil.copyProperties(dto, template);
        boolean b = this.updateById(template);
        if (!b) {
            throw new BusinessException("更新模板失败！！！");
        }
        LambdaQueryWrapper<TemplateApp> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TemplateApp::getTemplateId, dto.getTemplateId());
        TemplateApp templateApp = new TemplateApp();
        templateApp.setTemplateId(dto.getTemplateId());
        templateApp.setAppId(dto.getAppId());
        boolean update = templateAppService.update(templateApp, wrapper);
        if (!update) {
            throw new BusinessException("更新模板失败！！！");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(TemplateSaveAndUpdateRequestDto dto) {
        Template template = new Template();
        BeanUtil.copyProperties(dto, template);
        boolean save = this.save(template);
        if (!save) {
            throw new BusinessException("保存模板失败！！！");
        }
        TemplateApp templateApp = new TemplateApp();
        templateApp.setTemplateId(template.getTemplateId());
        templateApp.setAppId(dto.getAppId());
        boolean b = templateAppService.save(templateApp);
        if (!b) {
            throw new BusinessException("保存模板与 app 关系失败！！！");
        }
    }


}




