package com.oszero.deliver.server.web.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.oszero.deliver.server.constant.TraceIdConstant;
import com.oszero.deliver.server.enums.StatusEnum;
import com.oszero.deliver.server.exception.BusinessException;
import com.oszero.deliver.server.log.MessageLinkTraceLogger;
import com.oszero.deliver.server.model.dto.PushWayDto;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.model.dto.request.SendRequestDto;
import com.oszero.deliver.server.model.entity.App;
import com.oszero.deliver.server.model.entity.Template;
import com.oszero.deliver.server.model.entity.TemplateApp;
import com.oszero.deliver.server.pretreatment.link.LinkContext;
import com.oszero.deliver.server.pretreatment.link.LinkHandler;
import com.oszero.deliver.server.pretreatment.link.LinkModel;
import com.oszero.deliver.server.util.IpUtils;
import com.oszero.deliver.server.util.MDCUtils;
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
    private final MessageLinkTraceLogger messageLinkTraceLogger;


    @Override
    public String send(SendRequestDto sendRequestDto) {

        // 1.通过 templateId 获取 template
        Long templateId = sendRequestDto.getTemplateId();
        Template template = templateService.getById(templateId);
        if (Objects.isNull(template)) {
            throw new BusinessException("传入的模板 ID 非法，请输入正确的 templateId ！！！");
        }
        // 关闭状态直接返回
        if (StatusEnum.OFF.getStatus().equals(template.getTemplateStatus())) {
            throw new BusinessException("此模板已关闭，再次使用请启用此模板！！！");
        }
        messageLinkTraceLogger.info("消息链路 ID: {}, 模板 ID: {}, 应用 ID: {}, 接收人列表: {}, 是否重试消息: {}, 重试次数剩余: {}, 请求 IP: {}, 处理信息: {}"
                ,MDCUtils.get(TraceIdConstant.TRACE_ID)
                ,templateId
                ,null
                ,sendRequestDto.getUsers()
                ,null
                ,sendRequestDto.getRetry()
                ,IpUtils.getClientIp()
                ,"成功获取到消息模版id");

        // 2.通过 templateId 获取 appId
        LambdaQueryWrapper<TemplateApp> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TemplateApp::getTemplateId, templateId);
        TemplateApp templateApp = templateAppService.getOne(wrapper);
        if (Objects.isNull(templateApp)) {
            throw new BusinessException("未获取到模板所关联的应用，请检查关联的应用是否存在！！！");
        }

        Long appId = templateApp.getAppId();

        messageLinkTraceLogger.info("消息链路 ID: {}, 模板 ID: {}, 应用 ID: {}, 接收人列表: {}, 是否重试消息: {}, 重试次数剩余: {}, 请求 IP: {}, 处理信息: {}"
                ,MDCUtils.get(TraceIdConstant.TRACE_ID)
                ,templateId
                ,appId
                ,sendRequestDto.getUsers()
                ,null
                ,sendRequestDto.getRetry()
                ,IpUtils.getClientIp()
                ,"成功获取到AppId");

        // 3.得到 appConfig
        App app = appService.getById(appId);
        if (Objects.isNull(app)) {
            throw new BusinessException("未获取到模板所关联的应用，请检查关联的应用是否存在！！！");
        }
        // 关闭状态直接返回
        if (StatusEnum.OFF.getStatus().equals(app.getAppStatus())) {
            throw new BusinessException("模板关联的应用为禁用状态，再次使用请启用！！！");
        }
        String appConfig = app.getAppConfig();

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
                .templateId(templateId)
                .appId(appId)
                .users(users)
                .usersType(usersType)
                .pushRange(pushRange)
                .appConfig(appConfig)
                .paramMap(paramMap)
                .channelType(channelType)
                .messageType(messageType)
                .traceId(MDCUtils.get(TraceIdConstant.TRACE_ID)) // 全局链路 id
                .retry(retry).build();



        messageLinkTraceLogger.info("消息链路 ID: {}, 模板 ID: {}, 应用 ID: {}, 接收人列表: {}, 是否重试消息: {}, 重试次数剩余: {}, 请求 IP: {}, 处理信息: {}"
                ,MDCUtils.get(TraceIdConstant.TRACE_ID)
                ,templateId
                ,appId
                ,sendRequestDto.getUsers()
                ,sendTaskDto.getRetried()
                ,sendRequestDto.getRetry()
                ,IpUtils.getClientIp()
                ,"sendTaskDto封装完成");

        // 6.处理相关责任链
        LinkContext<LinkModel> context = LinkContext.builder()
                .processModel(sendTaskDto)
                .code(usersType + "-" + channelType).build();
        linkHandler.process(context);

        // 7.返回 TRACE_ID
        return MDCUtils.get(TraceIdConstant.TRACE_ID);
    }
}
