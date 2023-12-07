package com.oszero.deliver.server.message.consumer.rocketmq;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.constant.MQConstant;
import com.oszero.deliver.server.constant.TraceIdConstant;
import com.oszero.deliver.server.log.MessageLinkTraceLogger;
import com.oszero.deliver.server.message.consumer.handler.impl.DingHandler;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.util.IpUtils;
import com.oszero.deliver.server.util.MDCUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * 钉钉 RocketMQConsumer
 *
 * @author oszero
 * @version 1.0.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
@RocketMQMessageListener(topic = MQConstant.DING_TOPIC, consumerGroup = MQConstant.DING_CONSUMER_GROUP)
@ConditionalOnProperty(value = "mq-type", havingValue = "rocketmq")
public class DingConsumer implements RocketMQListener<MessageExt> {

    private final DingHandler dingHandler;
    private final RocketMQCommonConsumer commonConsumer;
    private final MessageLinkTraceLogger messageLinkTraceLogger;

    /**
     * 没有报错，就签收
     * 如果没有报错，就是拒收 就会重试
     *
     * @param messageExt 消息对象
     */
    @Override
    public void onMessage(MessageExt messageExt) {
        log.info("[DingConsumer 接收到消息] {}", messageExt);

        SendTaskDto sendTaskDto = JSONUtil.toBean(
                new String(messageExt.getBody(), StandardCharsets.UTF_8
                ), SendTaskDto.class);
        MDCUtils.put(TraceIdConstant.TRACE_ID, sendTaskDto.getTraceId());

        messageLinkTraceLogger.info("消息链路 ID: {}, 模板 ID: {}, 应用 ID: {}, 接收人列表: {}, 是否重试消息: {}, 重试次数剩余: {}, 请求 IP: {}, 处理信息: {}"
                , MDCUtils.get(TraceIdConstant.TRACE_ID)
                , sendTaskDto.getTemplateId()
                , sendTaskDto.getAppId()
                , sendTaskDto.getUsers()
                , sendTaskDto.getRetried()
                , sendTaskDto.getRetry()
                , IpUtils.getClientIp()
                , "[DingConsumer 接收到消息] {}", messageExt);
        commonConsumer.omMessageAck(messageExt, dingHandler);

    }

}
