package com.oszero.deliver.server.message.consumer.rocketmq;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.constant.TraceIdConstant;
import com.oszero.deliver.server.enums.StatusEnum;
import com.oszero.deliver.server.log.MessageLinkTraceLogger;
import com.oszero.deliver.server.message.consumer.handler.BaseHandler;
import com.oszero.deliver.server.message.producer.Producer;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.util.IpUtils;
import com.oszero.deliver.server.util.MDCUtils;
import com.oszero.deliver.server.web.service.MessageRecordService;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * RocketMQ 通用消费者
 *
 * @author oszero
 * @version 1.0.0
 */
@Component
@RequiredArgsConstructor
public class CommonConsumer {

    private final MessageRecordService messageRecordService;
    private final Producer producer;
    private final MessageLinkTraceLogger messageLinkTraceLogger;

    public void omMessageAck(MessageExt messageExt, BaseHandler handler) {
        SendTaskDto sendTaskDto = null;
        try {
            sendTaskDto = JSONUtil.toBean(
                    new String(messageExt.getBody(), StandardCharsets.UTF_8
                    ), SendTaskDto.class);
            handler.doHandle(sendTaskDto);
            return;
        } catch (Exception exception) {
            if (!Objects.isNull(sendTaskDto) && sendTaskDto.getRetry() > 0) {
                // 记录消息消费失败
                SendTaskDto finalSendTaskDto = sendTaskDto;
                sendTaskDto.getUsers()
                        .forEach(
                                user -> messageRecordService.saveMessageRecord(finalSendTaskDto, StatusEnum.OFF, user)
                        );

                // 重新发送
                sendTaskDto.setRetry(sendTaskDto.getRetry() - 1);
                sendTaskDto.setRetried(StatusEnum.ON.getStatus());
                producer.sendMessage(sendTaskDto);
            }
        }

        messageLinkTraceLogger.error("消息链路 ID: {}, 模板 ID: {}, 应用 ID: {}, 接收人列表: {}, 是否重试消息: {}, 重试次数剩余: {}, 请求 IP: {}, 处理信息: {}"
                , MDCUtils.get(TraceIdConstant.TRACE_ID)
                , sendTaskDto.getTemplateId()
                , sendTaskDto.getAppId()
                , sendTaskDto.getUsers()
                , sendTaskDto.getRetried()
                , sendTaskDto.getRetry()
                , IpUtils.getClientIp()
                , "消息消费失败");

    }
}
