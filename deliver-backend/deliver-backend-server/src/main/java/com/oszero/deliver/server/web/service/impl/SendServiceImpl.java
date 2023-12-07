package com.oszero.deliver.server.web.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.oszero.deliver.server.constant.TraceIdConstant;
import com.oszero.deliver.server.enums.StatusEnum;
import com.oszero.deliver.server.exception.MessageException;
import com.oszero.deliver.server.model.dto.PushWayDto;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.model.dto.request.SendRequestDto;
import com.oszero.deliver.server.model.entity.App;
import com.oszero.deliver.server.model.entity.Template;
import com.oszero.deliver.server.model.entity.TemplateApp;
import com.oszero.deliver.server.pretreatment.link.LinkContext;
import com.oszero.deliver.server.pretreatment.link.LinkHandler;
import com.oszero.deliver.server.util.IpUtils;
import com.oszero.deliver.server.util.MDCUtils;
import com.oszero.deliver.server.util.MessageLinkTraceUtils;
import com.oszero.deliver.server.web.service.AppService;
import com.oszero.deliver.server.web.service.SendService;
import com.oszero.deliver.server.web.service.TemplateAppService;
import com.oszero.deliver.server.web.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 发送消息操作 Service 实现
 *
 * @author oszero
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class SendServiceImpl implements SendService {

    private final TemplateService templateService;
    private final TemplateAppService templateAppService;
    private final AppService appService;
    private final LinkHandler linkHandler;

    @Override
    public String send(SendRequestDto sendRequestDto) {

        // 1.设置 sendTaskDto
        SendTaskDto sendTaskDto = new SendTaskDto();

        sendTaskDto.setTemplateId(sendRequestDto.getTemplateId());
        sendTaskDto.setParamMap(sendRequestDto.getParamMap());
        sendTaskDto.setTraceId(MDCUtils.get(TraceIdConstant.TRACE_ID));
        sendTaskDto.setClientIp(IpUtils.getClientIp());
        sendTaskDto.setUsers(sendRequestDto.getUsers());
        sendTaskDto.setRetried(0);
        sendTaskDto.setRetry(sendRequestDto.getRetry());

        MessageLinkTraceUtils.recordMessageLifecycleInfoLog(sendTaskDto, "服务端接收到接入方推送消息");

        // 2.通过 templateId 获取 template
        Long templateId = sendRequestDto.getTemplateId();

        Template template = templateService.getById(templateId);
        if (Objects.isNull(template)) {
            throw new MessageException(MessageLinkTraceUtils.formatMessageLifecycleErrorLogMsg(sendTaskDto, "传入的模板 ID 非法，请输入正确的 templateId ！！！"));
        }
        // 关闭状态直接返回
        if (StatusEnum.OFF.getStatus().equals(template.getTemplateStatus())) {
            throw new MessageException(MessageLinkTraceUtils.formatMessageLifecycleErrorLogMsg(sendTaskDto, "此模板已禁用，再次使用请启用此模板！！！"));
        }

        Integer pushRange = template.getPushRange();
        Integer usersType = template.getUsersType();
        PushWayDto pushWayDto = JSONUtil.toBean(template.getPushWays(), PushWayDto.class);
        Integer channelType = pushWayDto.getChannelType();
        String messageType = pushWayDto.getMessageType();

        sendTaskDto.setPushRange(pushRange);
        sendTaskDto.setUsersType(usersType);
        sendTaskDto.setChannelType(channelType);
        sendTaskDto.setMessageType(messageType);

        MessageLinkTraceUtils.recordMessageLifecycleInfoLog(sendTaskDto, "完成消息模板检测");

        // 3.通过 templateId 获取 appId
        LambdaQueryWrapper<TemplateApp> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TemplateApp::getTemplateId, templateId);
        TemplateApp templateApp = templateAppService.getOne(wrapper);
        if (Objects.isNull(templateApp)) {
            throw new MessageException(MessageLinkTraceUtils.formatMessageLifecycleErrorLogMsg(sendTaskDto, "未获取到模板所关联的应用，请检查关联的应用是否存在！！！"));
        }

        Long appId = templateApp.getAppId();
        sendTaskDto.setAppId(appId);

        // 4.得到 appConfig
        App app = appService.getById(appId);
        if (Objects.isNull(app)) {
            throw new MessageException(MessageLinkTraceUtils.formatMessageLifecycleErrorLogMsg(sendTaskDto, "未获取到模板所关联的应用，请检查关联的应用是否存在！！！"));
        }
        // 关闭状态直接返回
        if (StatusEnum.OFF.getStatus().equals(app.getAppStatus())) {
            throw new MessageException(MessageLinkTraceUtils.formatMessageLifecycleErrorLogMsg(sendTaskDto, "模板关联的应用为禁用状态，再次使用请启用！！！"));
        }
        String appConfig = app.getAppConfig();
        sendTaskDto.setAppConfig(appConfig);

        MessageLinkTraceUtils.recordMessageLifecycleInfoLog(sendTaskDto, "完成消息模板关联应用检测");

        // 5.处理相关责任链
        LinkContext<SendTaskDto> context = LinkContext.<SendTaskDto>builder()
                .processModel(sendTaskDto)
                .code(usersType + "-" + channelType).build();
        linkHandler.process(context);

        // 6.返回 TraceId
        return sendTaskDto.getTraceId();
    }
}
