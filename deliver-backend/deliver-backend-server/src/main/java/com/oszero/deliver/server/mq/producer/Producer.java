package com.oszero.deliver.server.mq.producer;

import com.oszero.deliver.server.model.dto.SendTaskDto;

/**
 * 生产者接口
 *
 * @author oszero
 * @version 1.0.0
 */
public interface Producer {

    void sendMessage(SendTaskDto sendTaskDto);
}
