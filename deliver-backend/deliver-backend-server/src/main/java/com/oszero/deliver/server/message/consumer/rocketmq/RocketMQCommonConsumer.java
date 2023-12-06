package com.oszero.deliver.server.message.consumer.rocketmq;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.constant.TraceIdConstant;
import com.oszero.deliver.server.enums.StatusEnum;
import com.oszero.deliver.server.message.consumer.handler.BaseHandler;
import com.oszero.deliver.server.message.producer.Producer;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.util.MDCUtils;
import com.oszero.deliver.server.web.service.MessageRecordService;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
@ConditionalOnProperty(value = "mq-type", havingValue = "rocketmq")
public class RocketMQCommonConsumer {

    private final MessageRecordService messageRecordService;
    private final Producer producer;

    public void omMessageAck(MessageExt messageExt, BaseHandler handler) {
        SendTaskDto sendTaskDto = null;
        try {
            sendTaskDto = JSONUtil.toBean(new String(messageExt.getBody(), StandardCharsets.UTF_8), SendTaskDto.class);
            MDCUtils.put(TraceIdConstant.TRACE_ID, sendTaskDto.getTraceId());
            handler.doHandle(sendTaskDto);
        } catch (Exception exception) {
            if (!Objects.isNull(sendTaskDto) && sendTaskDto.getRetry() > 0) {
                // 记录消息消费失败
                SendTaskDto finalSendTaskDto = sendTaskDto;
                sendTaskDto.getUsers().forEach(user -> messageRecordService.saveMessageRecord(finalSendTaskDto, StatusEnum.OFF, user));

                // 重新发送
                sendTaskDto.setRetry(sendTaskDto.getRetry() - 1);
                sendTaskDto.setRetried(StatusEnum.ON.getStatus());
                producer.sendMessage(sendTaskDto);
            }
        }
    }
}
