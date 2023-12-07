package com.oszero.deliver.server.pretreatment.link.send;

import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.util.MessageLinkTraceUtils;

/**
 * 抽象类 send
 *
 * @author oszero
 * @version 1.0.0
 */
public abstract class CommonSend {

    public void sendToMq(SendTaskDto sendTaskDto) {
        MessageLinkTraceUtils.recordMessageLifecycleInfoLog(sendTaskDto, "开始准备发送消息到消息队列");
        send(sendTaskDto);
    }

    abstract void send(SendTaskDto sendTaskDto);

}
