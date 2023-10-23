package com.oszero.deliver.server.web.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.oszero.deliver.server.enums.ResultEnum;
import com.oszero.deliver.server.enums.StatusEnum;
import com.oszero.deliver.server.exception.BusinessException;
import com.oszero.deliver.server.model.dto.PushWayDto;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.model.dto.request.SendRequestDto;
import com.oszero.deliver.server.model.entity.App;
import com.oszero.deliver.server.model.entity.Template;
import com.oszero.deliver.server.model.entity.TemplateApp;
import com.oszero.deliver.server.pretreatment.link.LinkContext;
import com.oszero.deliver.server.pretreatment.link.LinkHandler;
import com.oszero.deliver.server.pretreatment.link.LinkModel;
import com.oszero.deliver.server.web.service.AppService;
import com.oszero.deliver.server.web.service.SendService;
import com.oszero.deliver.server.web.service.TemplateAppService;
import com.oszero.deliver.server.web.service.TemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 发送消息操作 Service 实现
 *
 * @author oszero
 * @version 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SendServiceImpl implements SendService {

    private final TemplateService templateService;
    private final TemplateAppService templateAppService;
    private final AppService appService;
    private final LinkHandler linkHandler;

    @Override
    public Integer send(SendRequestDto sendRequestDto) {

        // 1.通过 templateId 获取 template
        Integer templateId = sendRequestDto.getTemplateId();
        Template template = templateService.getById(templateId);
        if (Objects.isNull(template)) {
            throw new BusinessException(ResultEnum.CLIENT_ERROR.getMessage()); // todo: 后续统一设计系统所有异常
        }
        // 关闭状态直接返回
        if (StatusEnum.OFF.getStatus().equals(template.getTemplateStatus())) {
            throw new BusinessException(ResultEnum.CLIENT_ERROR.getMessage()); // todo: 后续统一设计系统所有异常
        }

        // 2.通过 templateId 获取 appId
        LambdaQueryWrapper<TemplateApp> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TemplateApp::getTemplateId, templateId);
        TemplateApp templateApp = templateAppService.getOne(wrapper);
        if (Objects.isNull(templateApp)) {
            throw new BusinessException(ResultEnum.CLIENT_ERROR.getMessage()); // todo: 后续统一设计系统所有异常
        }
        Long appId = templateApp.getAppId();

        // 3.得到 appConfig
        App app = appService.getById(appId);
        // 关闭状态直接返回
        if (StatusEnum.OFF.getStatus().equals(app.getAppStatus())) {
            throw new BusinessException(ResultEnum.CLIENT_ERROR.getMessage()); // todo: 后续统一设计系统所有异常
        }
        String appConfigJson = app.getAppConfig();

        // 4.得到各级参数
        List<String> users = sendRequestDto.getUsers();
        Map<String, Object> paramMap = sendRequestDto.getParamMap();
        Integer retry = sendRequestDto.getRetry();
        Integer pushRange = template.getPushRange();
        Integer usersType = template.getUsersType();
        PushWayDto pushWayDto = JSONUtil.toBean(template.getPushWays(), PushWayDto.class);
        Integer channelType = pushWayDto.getChannelType();
        String messageType = pushWayDto.getMessageType();

        // 5.组装 sendTaskDto
        SendTaskDto sendTaskDto = SendTaskDto.builder()
                .users(users)
                .usersType(usersType)
                .pushRange(pushRange)
                .appConfigJson(appConfigJson)
                .paramMap(paramMap)
                .channelType(channelType)
                .messageType(messageType)
                .retry(retry).build();

        // 6.处理相关责任链
        LinkContext<LinkModel> context = LinkContext.builder()
                .processModel(sendTaskDto)
                .code(usersType + "-" + channelType).build();
        linkHandler.process(context);
        return null;
    }
}
