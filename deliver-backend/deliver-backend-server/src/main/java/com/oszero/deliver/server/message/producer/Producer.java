package com.oszero.deliver.server.message.producer;

import com.oszero.deliver.server.model.dto.SendTaskDto;

public interface Producer {

    void sendMessage(SendTaskDto sendTaskDto);
}
