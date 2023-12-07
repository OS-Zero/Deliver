package com.oszero.deliver.server.message.consumer.rocketmq;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.constant.TraceIdConstant;
import com.oszero.deliver.server.enums.StatusEnum;
import com.oszero.deliver.server.message.consumer.handler.BaseHandler;
import com.oszero.deliver.server.message.producer.Producer;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.util.MDCUtils;
import com.oszero.deliver.server.util.MessageLinkTraceUtils;
import com.oszero.deliver.server.web.service.MessageRecordService;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

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

            MessageLinkTraceUtils.recordMessageLifecycleInfoLog(sendTaskDto, "接收到 RocketMQ 消息，消息已送达消费者");

            handler.doHandle(sendTaskDto);
        } catch (Exception exception) {
            if (sendTaskDto != null) {
                MessageLinkTraceUtils.recordMessageLifecycleErrorLog(exception.getMessage());

                // 记录消息消费失败
                SendTaskDto finalSendTaskDto = sendTaskDto;
                sendTaskDto.getUsers().forEach(user -> messageRecordService.saveMessageRecord(finalSendTaskDto, StatusEnum.OFF, user));

                if (sendTaskDto.getRetry() > 0) {
                    // 重新发送
                    sendTaskDto.setRetry(sendTaskDto.getRetry() - 1);
                    sendTaskDto.setRetried(StatusEnum.ON.getStatus());
                    producer.sendMessage(sendTaskDto);

                    MessageLinkTraceUtils.recordMessageLifecycleInfoLog(sendTaskDto, "RocketMQ 重试消息已发送");
                } else {
                    // TODO:后续可监控告警上报
                    MessageLinkTraceUtils.recordMessageLifecycleErrorLog(sendTaskDto, "RocketMQ 消息发送失败，重试次数已用完！！！");
                }
            } else {
                MessageLinkTraceUtils.recordMessageLifecycleErrorLog("消息消费失败，" + exception.getMessage() + "！！！");
            }
        }
    }

}
