package com.oszero.deliver.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oszero.deliver.admin.cache.ServerCacheManager;
import com.oszero.deliver.admin.constant.MessageParamConstant;
import com.oszero.deliver.admin.enums.MessageTypeEnum;
import com.oszero.deliver.admin.exception.BusinessException;
import com.oszero.deliver.admin.mapper.TemplateMapper;
import com.oszero.deliver.admin.model.CommonResult;
import com.oszero.deliver.admin.model.dto.request.*;
import com.oszero.deliver.admin.model.dto.response.MessageTypeResponseDto;
import com.oszero.deliver.admin.model.dto.response.TemplateSearchResponseDto;
import com.oszero.deliver.admin.model.entity.App;
import com.oszero.deliver.admin.model.entity.Template;
import com.oszero.deliver.admin.model.entity.TemplateApp;
import com.oszero.deliver.admin.service.AppService;
import com.oszero.deliver.admin.service.TemplateAppService;
import com.oszero.deliver.admin.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    private final ServerCacheManager serverCacheManager;

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
            BeanUtil.copyProperties(template, templateSearchResponseDto);

            templateSearchResponseDto.setAppId(app.getAppId());
            templateSearchResponseDto.setAppName(app.getAppName());

            return templateSearchResponseDto;
        }).collect(Collectors.toList());

        Page<TemplateSearchResponseDto> pageResp = new Page<>(templatePage.getPages(), templatePage.getSize());
        pageResp.setRecords(collect);
        pageResp.setTotal(templatePage.getTotal());
        return pageResp;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
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
        // 删除缓存
        dto.getIds().forEach(serverCacheManager::evictTemplate);
        dto.getIds().forEach(serverCacheManager::evictTemplateApp);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void updateById(TemplateSaveAndUpdateRequestDto dto) {
        // 去掉两边空格
        dto.setTemplateName(dto.getTemplateName().trim());

        checkTemplateNameIsDuplicate(dto);

        Template template = new Template();
        BeanUtil.copyProperties(dto, template);
        boolean b = this.updateById(template);
        if (!b) {
            throw new BusinessException("更新模板失败！！！");
        }

        LambdaQueryWrapper<TemplateApp> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TemplateApp::getTemplateId, dto.getTemplateId());

        TemplateApp templateApp = new TemplateApp();
        templateApp.setAppId(dto.getAppId());
        boolean update = templateAppService.update(templateApp, wrapper);
        if (!update) {
            throw new BusinessException("更新模板失败！！！");
        }
        // 删除缓存
        serverCacheManager.evictTemplate(dto.getTemplateId());
        serverCacheManager.evictTemplateApp(dto.getTemplateId());
    }

    @Override
    public void updateStatusById(TemplateUpdateStatusRequestDto dto) {
        Template template = new Template();
        BeanUtil.copyProperties(dto, template);
        boolean b = this.updateById(template);
        if (!b) {
            throw new BusinessException("更新模板失败！！！");
        }
        // 删除缓存
        serverCacheManager.evictTemplate(dto.getTemplateId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(TemplateSaveAndUpdateRequestDto dto) {
        // 去掉两边空格
        dto.setTemplateName(dto.getTemplateName().trim());

        checkTemplateNameIsDuplicate(dto);

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

    private void checkTemplateNameIsDuplicate(TemplateSaveAndUpdateRequestDto dto) {
        String templateName = dto.getTemplateName();
        Long templateId = dto.getTemplateId();

        LambdaQueryWrapper<Template> wrapper = new LambdaQueryWrapper<>();

        // 为 null 表示新增
        if (Objects.isNull(templateId)) {
            wrapper.eq(Template::getTemplateName, templateName);
            Template one = this.getOne(wrapper);
            if (!Objects.isNull(one)) {
                throw new BusinessException("此模板名(" + templateName + ")已存在！！！");
            }
        } else {
            wrapper.eq(Template::getTemplateName, templateName)
                    .or().eq(Template::getTemplateId, templateId);
            List<Template> list = this.list(wrapper);
            if (list.size() > 1) {
                throw new BusinessException("此模板名(" + templateName + ")已存在！！！");
            }
        }
    }

    @Override
    public List<MessageTypeResponseDto> getMessageTypeByChannelType(TemplateAddGetByChannelRequestDto dto) {
        final Integer channelType = dto.getChannelType();
        List<MessageTypeResponseDto> list = new ArrayList<>();

        MessageTypeResponseDto messageTypeResponseDto = new MessageTypeResponseDto();
        messageTypeResponseDto.setCode(MessageTypeEnum.TEXT.getCode());
        messageTypeResponseDto.setName(MessageTypeEnum.TEXT.getName());
        list.add(messageTypeResponseDto);
        if (channelType > 3) {
            for (MessageTypeEnum v : MessageTypeEnum.values()) {
                Integer ct = v.getChannelType();
                if (ct.equals(channelType)) {
                    MessageTypeResponseDto msg = new MessageTypeResponseDto();
                    msg.setCode(v.getCode());
                    msg.setName(v.getName());
                    list.add(msg);
                }
            }
        }
        return list;
    }

    @Override
    public void testSendMessage(SendTestRequestDto sendTestRequestDto) {

        // 修改为自己的地址
        String serverUrl = "http://localhost:7070/open/sendMessage";
        CommonResult<?> commonResult;
        try (HttpResponse response = HttpRequest.post(serverUrl)
                .header("ContentType", "application/json")
                .body(JSONUtil.toJsonStr(sendTestRequestDto))
                .execute()) {
            commonResult = JSONUtil.toBean(response.body(), CommonResult.class);
        } catch (Exception e) {
            throw new BusinessException("测试发送接口失败，服务端异常，请检查！！！");
        }
        if (Objects.isNull(commonResult)) {
            throw new BusinessException("测试发送接口失败，服务端异常，请检查！！！");
        }
        if (!commonResult.getCode().equals(200)) {
            throw new BusinessException(commonResult.getErrorMessage());
        }
    }

    @Override
    public String getMessageParamByMessageType(TemplateMessageParamByMessageTypeRequestDto dto) {
        String messageType = dto.getMessageType();
        Integer channelType = dto.getChannelType();
        return MessageParamConstant.MESSAGE_PARAM_MAP.getOrDefault(channelType + MessageParamConstant.H + messageType, "{}");
    }
}




