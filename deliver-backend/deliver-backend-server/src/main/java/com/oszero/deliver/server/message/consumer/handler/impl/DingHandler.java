package com.oszero.deliver.server.message.consumer.handler.impl;

import com.oszero.deliver.server.message.consumer.handler.BaseHandler;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.util.channel.DingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DingHandler extends BaseHandler {

    private final DingUtils dingUtils;

    @Override
    protected void handle(SendTaskDto sendTaskDto) throws Exception {

    }
}
