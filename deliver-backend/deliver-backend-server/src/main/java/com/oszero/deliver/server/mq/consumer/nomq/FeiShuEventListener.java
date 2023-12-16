package com.oszero.deliver.server.mq.consumer.nomq;

import com.oszero.deliver.server.handler.impl.FeiShuHandler;
import com.oszero.deliver.server.model.event.FeiShuEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 飞书事件监听器
 *
 * @author oszero
 * @version 1.0.0
 */
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "mq-type", havingValue = "none")
public class FeiShuEventListener implements ApplicationListener<FeiShuEvent> {

    private final NoMQCommonListener noMQCommonListener;
    private final FeiShuHandler feiShuHandler;

    @Async("feiShuAsyncExecutor")
    @Override
    public void onApplicationEvent(FeiShuEvent event) {
        noMQCommonListener.omMessageAck(event, feiShuHandler);
    }
}
