package com.oszero.deliver.server.message.consumer.rocketmq;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.constant.MQConstant;
import com.oszero.deliver.server.message.consumer.handler.impl.FeiShuHandler;
import com.oszero.deliver.server.message.producer.Producer;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
@RocketMQMessageListener(
        topic = MQConstant.FEI_SHU_TOPIC,
        consumerGroup = MQConstant.FEI_SHU_CONSUMER_GROUP
)
@ConditionalOnProperty(value = "mq-type", havingValue = "rocketmq")
public class FeiShuConsumer implements RocketMQListener<MessageExt> {

    private final FeiShuHandler feiShuHandler;

    private final Producer producer;

    /**
     * 没有报错，就签收
     * 如果没有报错，就是拒收 就会重试
     *
     * @param messageExt 消息对象
     */
    @SneakyThrows
    @Override
    public void onMessage(MessageExt messageExt) {
        log.info("[MailConsumer 接收到消息] {}", messageExt);
        SendTaskDto sendTaskDto = null;
        try {
            sendTaskDto = JSONUtil.toBean(
                    new String(messageExt.getBody(), StandardCharsets.UTF_8
                    ), SendTaskDto.class);
            feiShuHandler.doHandle(sendTaskDto);
        } catch (Exception exception) {
            if (!Objects.isNull(sendTaskDto) && sendTaskDto.getRetry() > 0) {
                sendTaskDto.setRetry(sendTaskDto.getRetry() - 1);
                producer.sendMessage(sendTaskDto);
            }
        }
    }
}
