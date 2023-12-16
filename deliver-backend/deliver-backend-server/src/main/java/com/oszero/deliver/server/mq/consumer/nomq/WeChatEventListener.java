package com.oszero.deliver.server.mq.consumer.nomq;

import com.oszero.deliver.server.handler.impl.WeChatHandler;
import com.oszero.deliver.server.model.event.WeChatEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 企微事件监听器
 *
 * @author oszero
 * @version 1.0.0
 */
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "mq-type", havingValue = "none")
public class WeChatEventListener implements ApplicationListener<WeChatEvent> {

    private final NoMQCommonListener noMQCommonListener;
    private final WeChatHandler weChatHandler;

    @Async("weChatAsyncExecutor")
    @Override
    public void onApplicationEvent(WeChatEvent event) {
        noMQCommonListener.omMessageAck(event, weChatHandler);
    }
}
