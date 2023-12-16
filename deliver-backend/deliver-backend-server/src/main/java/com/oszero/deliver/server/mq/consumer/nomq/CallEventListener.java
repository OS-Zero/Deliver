package com.oszero.deliver.server.mq.consumer.nomq;

import com.oszero.deliver.server.handler.impl.CallHandler;
import com.oszero.deliver.server.model.event.CallEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 电话事件监听器
 *
 * @author oszero
 * @version 1.0.0
 */
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "mq-type", havingValue = "none")
public class CallEventListener implements ApplicationListener<CallEvent> {

    private final NoMQCommonListener noMQCommonListener;
    private final CallHandler callHandler;

    @Async
    @Override
    public void onApplicationEvent(CallEvent event) {
        noMQCommonListener.omMessageAck(event, callHandler);
    }
}
