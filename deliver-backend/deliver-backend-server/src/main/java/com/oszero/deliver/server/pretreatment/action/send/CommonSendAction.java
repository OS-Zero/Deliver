package com.oszero.deliver.server.pretreatment.action.send;

import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.pretreatment.pipeline.ProcessContext;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class CommonSendAction {

    public void sendToMq(ProcessContext<SendTaskDto> context) {
        log.info("[CommonSendAction#sendToMq],{}", "发送消息");
        send(context.getProcessModel());

    }

    abstract boolean send(SendTaskDto sendTaskDto);

}
