package com.oszero.deliver.server.mq.consumer.nomq;

import com.oszero.deliver.server.handler.impl.DingHandler;
import com.oszero.deliver.server.model.event.DingEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 钉钉事件监听器
 *
 * @author oszero
 * @version 1.0.0
 */
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "mq-type", havingValue = "none")
public class DingEventListener implements ApplicationListener<DingEvent> {

    private final NoMQCommonListener noMQCommonListener;
    private final DingHandler dingHandler;

    @Async("dingAsyncExecutor")
    @Override
    public void onApplicationEvent(DingEvent event) {
        noMQCommonListener.omMessageAck(event, dingHandler);
    }
}
