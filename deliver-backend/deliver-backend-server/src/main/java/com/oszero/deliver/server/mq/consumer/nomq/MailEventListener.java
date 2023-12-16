package com.oszero.deliver.server.mq.consumer.nomq;

import com.oszero.deliver.server.handler.impl.MailHandler;
import com.oszero.deliver.server.model.event.MailEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 邮件事件监听器
 *
 * @author oszero
 * @version 1.0.0
 */
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "mq-type", havingValue = "none")
public class MailEventListener implements ApplicationListener<MailEvent> {

    private final NoMQCommonListener noMQCommonListener;
    private final MailHandler mailHandler;

    @Async("mailAsyncExecutor")
    @Override
    public void onApplicationEvent(MailEvent event) {
        noMQCommonListener.omMessageAck(event, mailHandler);
    }
}
