package com.oszero.deliver.server.message.consumer.handler;

import com.oszero.deliver.server.constant.TraceIdConstant;
import com.oszero.deliver.server.enums.StatusEnum;
import com.oszero.deliver.server.log.MessageLinkTraceLogger;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.util.IpUtils;
import com.oszero.deliver.server.util.MDCUtils;
import com.oszero.deliver.server.web.service.MessageRecordService;
import lombok.extern.slf4j.Slf4j;

/**
 * 消费者处理器抽象类
 *
 * @author oszero
 * @version 1.0.0
 */
@Slf4j
public abstract class BaseHandler {

    protected MessageRecordService messageRecordService;
    protected MessageLinkTraceLogger messageLinkTraceLogger;

    /**
     * 消息处理
     *
     * @param sendTaskDto 发送 Dto
     * @throws Exception 异常
     */
    public void doHandle(SendTaskDto sendTaskDto) throws Exception {
        // 1. todo:前置处理 日志
        // 2. 具体处理
        handle(sendTaskDto);

        // 3. 后置处理 记录消息，消息发送成功
        log.info("消息消费成功，记录消息");
        messageLinkTraceLogger.info("消息链路 ID: {}, 模板 ID: {}, 应用 ID: {}, 接收人列表: {}, 是否重试消息: {}, 重试次数剩余: {}, 请求 IP: {}, 处理信息: {}"
                , MDCUtils.get(TraceIdConstant.TRACE_ID)
                , sendTaskDto.getTemplateId()
                , sendTaskDto.getAppId()
                , sendTaskDto.getUsers()
                , sendTaskDto.getRetried()
                , sendTaskDto.getRetry()
                , IpUtils.getClientIp()
                , "消息消费成功");

        sendTaskDto.getUsers()
                .forEach(user ->
                        messageRecordService.saveMessageRecord(
                                sendTaskDto,
                                StatusEnum.ON,
                                user
                        ));

        messageLinkTraceLogger.info("消息链路 ID: {}, 模板 ID: {}, 应用 ID: {}, 接收人列表: {}, 是否重试消息: {}, 重试次数剩余: {}, 请求 IP: {}, 处理信息: {}"
                , MDCUtils.get(TraceIdConstant.TRACE_ID)
                , sendTaskDto.getTemplateId()
                , sendTaskDto.getAppId()
                , sendTaskDto.getUsers()
                , sendTaskDto.getRetried()
                , sendTaskDto.getRetry()
                , IpUtils.getClientIp()
                , "消息记录成功");
    }

    protected abstract void handle(SendTaskDto sendTaskDto) throws Exception;

}
