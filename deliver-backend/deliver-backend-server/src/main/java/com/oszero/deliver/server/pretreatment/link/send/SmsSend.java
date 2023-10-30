package com.oszero.deliver.server.pretreatment.link.send;

import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.pretreatment.link.BusinessLink;
import com.oszero.deliver.server.pretreatment.link.LinkContext;
import org.springframework.stereotype.Service;

/**
 * 短信 send
 *
 * @author oszero
 * @version 1.0.0
 */
@Service
public class SmsSend extends CommonSend implements BusinessLink<SendTaskDto> {


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

    }
}
