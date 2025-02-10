package com.oszero.deliver.business.server.config;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.dsl.Disruptor;
import com.oszero.deliver.business.server.constant.MQConstant;
import com.oszero.deliver.business.server.handler.impl.*;
import com.oszero.deliver.business.server.model.event.disruptor.DisruptorBaseEvent;
import com.oszero.deliver.business.server.mq.consumer.disruptor.DisruptorHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ThreadFactory;

/**
 * @author oszero
 * @version 1.0.0
 */
@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(value = MQConstant.MQ_TYPE, havingValue = MQConstant.MQ_TYPE_DISRUPTOR)
public class DisruptorConfig {

    private final CallHandler callHandler;
    private final SmsHandler smsHandler;
    private final MailHandler mailHandler;
    private final DingHandler dingHandler;
    private final WeChatHandler weChatHandler;
    private final FeiShuHandler feiShuHandler;
    private final OfficialAccountHandler officialAccountHandler;

    @Bean(MQConstant.CALL_DISRUPTOR)
    public Disruptor<DisruptorBaseEvent.CallEventDisruptor> callEventDisruptor() {
        EventFactory<DisruptorBaseEvent.CallEventDisruptor> eventFactory = DisruptorBaseEvent.CallEventDisruptor::new;
        int bufferSize = MQConstant.DISRUPTOR_BUFFER_SIZE;
        ThreadFactory threadFactory = r -> new Thread(r, MQConstant.DISRUPTOR_THREAD_NAME);
        Disruptor<DisruptorBaseEvent.CallEventDisruptor> disruptor = new Disruptor<>(eventFactory, bufferSize, threadFactory);
        disruptor.handleEventsWith(new DisruptorHandler.DisruptorCallEventHandler(callHandler));
        disruptor.start();
        return disruptor;
    }

    @Bean(MQConstant.SMS_DISRUPTOR)
    public Disruptor<DisruptorBaseEvent.SmsEventDisruptor> smsEventDisruptor() {
        EventFactory<DisruptorBaseEvent.SmsEventDisruptor> eventFactory = DisruptorBaseEvent.SmsEventDisruptor::new;
        int bufferSize = MQConstant.DISRUPTOR_BUFFER_SIZE;
        ThreadFactory threadFactory = r -> new Thread(r, MQConstant.DISRUPTOR_THREAD_NAME);
        Disruptor<DisruptorBaseEvent.SmsEventDisruptor> disruptor = new Disruptor<>(eventFactory, bufferSize, threadFactory);
        disruptor.handleEventsWith(new DisruptorHandler.DisruptorSmsEventHandler(smsHandler));
        disruptor.start();
        return disruptor;
    }

    @Bean(MQConstant.MAIL_DISRUPTOR)
    public Disruptor<DisruptorBaseEvent.MailEventDisruptor> mailEventDisruptor() {
        EventFactory<DisruptorBaseEvent.MailEventDisruptor> eventFactory = DisruptorBaseEvent.MailEventDisruptor::new;
        int bufferSize = MQConstant.DISRUPTOR_BUFFER_SIZE;
        ThreadFactory threadFactory = r -> new Thread(r, MQConstant.DISRUPTOR_THREAD_NAME);
        Disruptor<DisruptorBaseEvent.MailEventDisruptor> disruptor = new Disruptor<>(eventFactory, bufferSize, threadFactory);
        disruptor.handleEventsWith(new DisruptorHandler.DisruptorMailEventHandler(mailHandler));
        disruptor.start();
        return disruptor;
    }

    @Bean(MQConstant.DING_DISRUPTOR)
    public Disruptor<DisruptorBaseEvent.DingEventDisruptor> dingEventDisruptor() {
        EventFactory<DisruptorBaseEvent.DingEventDisruptor> eventFactory = DisruptorBaseEvent.DingEventDisruptor::new;
        int bufferSize = MQConstant.DISRUPTOR_BUFFER_SIZE;
        ThreadFactory threadFactory = r -> new Thread(r, MQConstant.DISRUPTOR_THREAD_NAME);
        Disruptor<DisruptorBaseEvent.DingEventDisruptor> disruptor = new Disruptor<>(eventFactory, bufferSize, threadFactory);
        disruptor.handleEventsWith(new DisruptorHandler.DisruptorDingEventHandler(dingHandler));
        disruptor.start();
        return disruptor;
    }

    @Bean(MQConstant.WECHAT_DISRUPTOR)
    public Disruptor<DisruptorBaseEvent.WeChatEventDisruptor> weChatEventDisruptor() {
        EventFactory<DisruptorBaseEvent.WeChatEventDisruptor> eventFactory = DisruptorBaseEvent.WeChatEventDisruptor::new;
        int bufferSize = MQConstant.DISRUPTOR_BUFFER_SIZE;
        ThreadFactory threadFactory = r -> new Thread(r, MQConstant.DISRUPTOR_THREAD_NAME);
        Disruptor<DisruptorBaseEvent.WeChatEventDisruptor> disruptor = new Disruptor<>(eventFactory, bufferSize, threadFactory);
        disruptor.handleEventsWith(new DisruptorHandler.DisruptorWeChatEventHandler(weChatHandler));
        disruptor.start();
        return disruptor;
    }

    @Bean(MQConstant.FEI_SHU_DISRUPTOR)
    public Disruptor<DisruptorBaseEvent.FeiShuEventDisruptor> feiShuEventDisruptor() {
        EventFactory<DisruptorBaseEvent.FeiShuEventDisruptor> eventFactory = DisruptorBaseEvent.FeiShuEventDisruptor::new;
        int bufferSize = MQConstant.DISRUPTOR_BUFFER_SIZE;
        ThreadFactory threadFactory = r -> new Thread(r, MQConstant.DISRUPTOR_THREAD_NAME);
        Disruptor<DisruptorBaseEvent.FeiShuEventDisruptor> disruptor = new Disruptor<>(eventFactory, bufferSize, threadFactory);
        disruptor.handleEventsWith(new DisruptorHandler.DisruptorFeiShuEventHandler(feiShuHandler));
        disruptor.start();
        return disruptor;
    }

    @Bean(MQConstant.OFFICIAL_ACCOUNT_DISRUPTOR)
    public Disruptor<DisruptorBaseEvent.OfficialAccountEventDisruptor> officialAccountEventDisruptor() {
        EventFactory<DisruptorBaseEvent.OfficialAccountEventDisruptor> eventFactory = DisruptorBaseEvent.OfficialAccountEventDisruptor::new;
        int bufferSize = MQConstant.DISRUPTOR_BUFFER_SIZE;
        ThreadFactory threadFactory = r -> new Thread(r, MQConstant.DISRUPTOR_THREAD_NAME);
        Disruptor<DisruptorBaseEvent.OfficialAccountEventDisruptor> disruptor = new Disruptor<>(eventFactory, bufferSize, threadFactory);
        disruptor.handleEventsWith(new DisruptorHandler.DisruptorOfficialAccountEventHandler(officialAccountHandler));
        disruptor.start();
        return disruptor;
    }
}
