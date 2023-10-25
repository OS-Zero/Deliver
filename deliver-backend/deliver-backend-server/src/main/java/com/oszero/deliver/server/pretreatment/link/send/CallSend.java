package com.oszero.deliver.server.pretreatment.link.send;

import com.oszero.deliver.server.enums.ChannelTypeEnum;
import com.oszero.deliver.server.message.producer.Producer;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.pretreatment.link.BusinessLink;
import com.oszero.deliver.server.pretreatment.link.LinkContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CallSend extends CommonSend implements BusinessLink<SendTaskDto> {

    private final Producer producer;

    /**
     * 真正处理逻辑
     *
     * @param context
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
