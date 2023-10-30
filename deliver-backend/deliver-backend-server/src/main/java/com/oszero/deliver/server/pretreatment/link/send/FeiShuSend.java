package com.oszero.deliver.server.pretreatment.link.send;

import com.oszero.deliver.server.message.producer.Producer;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.pretreatment.link.BusinessLink;
import com.oszero.deliver.server.pretreatment.link.LinkContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 飞书 send
 *
 * @author oszero
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class FeiShuSend extends CommonSend implements BusinessLink<SendTaskDto> {

    private final Producer producer;

    @Override
    public void process(LinkContext<SendTaskDto> context) {
        sendToMq(context.getProcessModel());
    }

    @Override
    void send(SendTaskDto sendTaskDto) {
        producer.sendMessage(sendTaskDto);
    }

}
