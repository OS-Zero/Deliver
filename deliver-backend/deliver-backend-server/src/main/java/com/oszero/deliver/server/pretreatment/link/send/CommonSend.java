package com.oszero.deliver.server.pretreatment.link.send;

import com.oszero.deliver.server.model.dto.SendTaskDto;
import lombok.extern.slf4j.Slf4j;

/**
 * 抽象类 send
 *
 * @author oszero
 * @version 1.0.0
 */
@Slf4j
public abstract class CommonSend {

    public void sendToMq(SendTaskDto sendTaskDto) {
        log.info("[CommonSend#sendToMq],{}", "发送消息");
        send(sendTaskDto);
    }

    abstract void send(SendTaskDto sendTaskDto);

}
