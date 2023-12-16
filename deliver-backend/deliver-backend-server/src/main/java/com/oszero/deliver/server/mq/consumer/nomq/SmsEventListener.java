package com.oszero.deliver.server.mq.consumer.nomq;

import com.oszero.deliver.server.handler.impl.SmsHandler;
import com.oszero.deliver.server.model.event.SmsEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 短信事件监听器
 *
 * @author oszero
 * @version 1.0.0
 */
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "mq-type", havingValue = "none")
public class SmsEventListener implements ApplicationListener<SmsEvent> {

    private final NoMQCommonListener noMQCommonListener;
    private final SmsHandler smsHandler;

    @Async
    @Override
    public void onApplicationEvent(SmsEvent event) {
        noMQCommonListener.omMessageAck(event, smsHandler);
    }
}
