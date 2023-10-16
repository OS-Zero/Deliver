package com.oszero.deliver.server.web.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.oszero.deliver.server.enums.PushRangeEnum;
import com.oszero.deliver.server.enums.ResultEnum;
import com.oszero.deliver.server.exception.BusinessException;
import com.oszero.deliver.server.model.dto.PushWayDto;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.model.dto.request.SendRequestDto;
import com.oszero.deliver.server.model.entity.Template;
import com.oszero.deliver.server.model.entity.TemplateApp;
import com.oszero.deliver.server.pretreatment.pipeline.ProcessContext;
import com.oszero.deliver.server.pretreatment.pipeline.ProcessHandler;
import com.oszero.deliver.server.pretreatment.pipeline.ProcessModel;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class SendServiceImpl implements SendService {

    private final TemplateService templateService;
    private final TemplateAppService templateAppService;
    private final ProcessHandler processHandler;

    @Override
    public Integer send(SendRequestDto sendRequestDto) {

        // 1.通过 templateId 获取 template
        Integer templateId = sendRequestDto.getTemplateId();
        Template template = templateService.getById(templateId);
        if (Objects.isNull(template)) {
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

        // 3.得到各级参数
        List<String> users = sendRequestDto.getUsers();
        Map<String, String> paramMap = sendRequestDto.getParamMap();
        Integer retry = sendRequestDto.getRetry();
        Integer pushRange = template.getPushRange();
        Integer usersType = template.getUsersType();
        PushWayDto pushWayDto = JSONUtil.toBean(template.getPushWays(), PushWayDto.class);
        Integer channelType = pushWayDto.getChannelType();
        String messageType = pushWayDto.getMessageType();

        // 4.组装 sendTaskDto
        SendTaskDto sendTaskDto = SendTaskDto.builder()
                .users(users)
                .usersType(usersType)
                .pushRange(pushRange)
                .appId(appId)
                .paramMap(paramMap)
                .channelType(channelType)
                .messageType(messageType)
                .retry(retry).build();

        // 5.处理相关责任链
        ProcessContext<ProcessModel> context = ProcessContext.builder()
                .processModel(sendTaskDto)
                .code(usersType + "-" + channelType).build();
        processHandler.process(context);
        return null;
    }
}
