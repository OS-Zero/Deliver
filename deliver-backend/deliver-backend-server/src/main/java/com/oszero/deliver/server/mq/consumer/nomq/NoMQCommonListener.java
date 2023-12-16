package com.oszero.deliver.server.mq.consumer.nomq;

import com.oszero.deliver.server.handler.BaseHandler;
import com.oszero.deliver.server.model.dto.common.SendTaskDto;
import com.oszero.deliver.server.model.event.BaseApplicationEvent;
import com.oszero.deliver.server.mq.consumer.common.MQCommonConsumer;
import com.oszero.deliver.server.mq.producer.Producer;
import com.oszero.deliver.server.web.service.MessageRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * 无 MQ 通用事件监听器
 *
 * @author oszero
 * @version 1.0.0
 */
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "mq-type", havingValue = "none")
public class NoMQCommonListener {

    private final MessageRecordService messageRecordService;
    private final Producer producer;

    public void omMessageAck(BaseApplicationEvent event, BaseHandler handler) {
        SendTaskDto sendTaskDto = event.getSendTaskDto();
        try {
            MQCommonConsumer.tryHandle(sendTaskDto, handler);
        } catch (Exception exception) {
            MQCommonConsumer.catchHandle(sendTaskDto, exception, messageRecordService, producer);
        }
    }
}
