package com.oszero.deliver.server.message.consumer.handler.impl;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.message.consumer.handler.BaseHandler;
import com.oszero.deliver.server.model.app.DingApp;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.util.channel.DingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 钉钉消费者处理器
 *
 * @author oszero
 * @version 1.0.0
 */
@Component
@RequiredArgsConstructor
public class DingHandler extends BaseHandler {

    private final DingUtils dingUtils;

    @Override
    protected void handle(SendTaskDto sendTaskDto) throws Exception {
        DingApp dingApp = JSONUtil.toBean(sendTaskDto.getAppConfigJson(), DingApp.class);
        String accessToken = dingUtils.getAccessToken(dingApp);
        dingUtils.sendMessage(accessToken, sendTaskDto);
    }
}
