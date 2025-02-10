package com.oszero.deliver.business.server.mq.producer.impl;

import com.lmax.disruptor.dsl.Disruptor;
import com.oszero.deliver.business.common.enums.ChannelTypeEnum;
import com.oszero.deliver.business.server.constant.MQConstant;
import com.oszero.deliver.business.server.exception.MessageException;
import com.oszero.deliver.business.server.model.dto.common.SendTaskDto;
import com.oszero.deliver.business.server.model.event.disruptor.DisruptorBaseEvent;
import com.oszero.deliver.business.server.mq.consumer.disruptor.DisruptorHandler;
import com.oszero.deliver.business.server.mq.producer.Producer;
import com.oszero.deliver.business.server.util.DisruptorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;

/**
 * @author oszero
 * @version 1.0.0
 */
@Component
@ConditionalOnProperty(value = MQConstant.MQ_TYPE, havingValue = MQConstant.MQ_TYPE_DISRUPTOR)
public class DisruptorProducer implements Producer {

    @Autowired
    @Qualifier(MQConstant.CALL_DISRUPTOR)
    private Disruptor<DisruptorBaseEvent.CallEventDisruptor> callEventDisruptor;
    @Autowired
    @Qualifier(MQConstant.SMS_DISRUPTOR)
    private Disruptor<DisruptorBaseEvent.SmsEventDisruptor> smsEventDisruptor;
    @Autowired
    @Qualifier(MQConstant.MAIL_DISRUPTOR)
    private Disruptor<DisruptorBaseEvent.MailEventDisruptor> mailEventDisruptor;
    @Autowired
    @Qualifier(MQConstant.DING_DISRUPTOR)
    private Disruptor<DisruptorBaseEvent.DingEventDisruptor> dingEventDisruptor;
    @Autowired
    @Qualifier(MQConstant.WECHAT_DISRUPTOR)
    private Disruptor<DisruptorBaseEvent.WeChatEventDisruptor> wechatEventDisruptor;
    @Autowired
    @Qualifier(MQConstant.FEI_SHU_DISRUPTOR)
    private Disruptor<DisruptorBaseEvent.FeiShuEventDisruptor> feiShuEventDisruptor;
    @Autowired
    @Qualifier(MQConstant.OFFICIAL_ACCOUNT_DISRUPTOR)
    private Disruptor<DisruptorBaseEvent.OfficialAccountEventDisruptor> officialAccountEventDisruptor;

    @PostConstruct
    public void initializer() {
        DisruptorHandler.PRODUCER = this;
    }

    @Override
    public void sendMessage(SendTaskDto sendTaskDto) {
        ChannelTypeEnum channelTypeEnum = ChannelTypeEnum.getInstanceByCode(sendTaskDto.getChannelType());
        if (Objects.isNull(channelTypeEnum)) {
            throw new MessageException("[DisruptorProducer#sendMessage] 渠道类型配置错误");
        }
        switch (channelTypeEnum) {
            case CALL -> DisruptorUtils.publishEvent(sendTaskDto, callEventDisruptor.getRingBuffer());
            case SMS -> DisruptorUtils.publishEvent(sendTaskDto, smsEventDisruptor.getRingBuffer());
            case MAIL -> DisruptorUtils.publishEvent(sendTaskDto, mailEventDisruptor.getRingBuffer());
            case DING -> DisruptorUtils.publishEvent(sendTaskDto, dingEventDisruptor.getRingBuffer());
            case WECHAT -> DisruptorUtils.publishEvent(sendTaskDto, wechatEventDisruptor.getRingBuffer());
            case FEI_SHU -> DisruptorUtils.publishEvent(sendTaskDto, feiShuEventDisruptor.getRingBuffer());
            case OFFICIAL_ACCOUNT -> DisruptorUtils.publishEvent(sendTaskDto, officialAccountEventDisruptor.getRingBuffer());
            default -> {
            }
        }
    }
}
