package com.oszero.deliver.server.message.consumer.rocketmq;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.enums.StatusEnum;
import com.oszero.deliver.server.message.consumer.handler.BaseHandler;
import com.oszero.deliver.server.message.producer.Producer;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.web.service.MessageRecordService;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CommonConsumer {

    private final MessageRecordService messageRecordService;
    private final Producer producer;

    public void omMessageAck(MessageExt messageExt, BaseHandler handler) {
        SendTaskDto sendTaskDto = null;
        try {
            sendTaskDto = JSONUtil.toBean(
                    new String(messageExt.getBody(), StandardCharsets.UTF_8
                    ), SendTaskDto.class);
            handler.doHandle(sendTaskDto);
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
    }
}
