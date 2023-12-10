package com.oszero.deliver.server.pretreatment.link.send;

import com.oszero.deliver.server.mq.producer.Producer;
import com.oszero.deliver.server.model.dto.common.SendTaskDto;
import com.oszero.deliver.server.pretreatment.common.MessageLink;
import com.oszero.deliver.server.pretreatment.common.LinkContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 邮箱推送
 *
 * @author oszero
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class MailSend extends CommonSend implements MessageLink<SendTaskDto> {

    private final Producer producer;

    /**
     * 真正处理逻辑
     *
     * @param context 上下文
     */
    @Override
    public void process(LinkContext<SendTaskDto> context) {
        sendToMq(context.getProcessModel());
    }

    @Override
    void send(SendTaskDto sendTaskDto) {
        producer.sendMessage(sendTaskDto);
    }
}
