package com.oszero.deliver.server.message.consumer.handler.impl;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.message.consumer.handler.BaseHandler;
import com.oszero.deliver.server.model.app.AppConfig;
import com.oszero.deliver.server.model.app.FeiShuApp;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.util.channel.FeiShuUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FeiShuHandler extends BaseHandler {

    private final FeiShuUtils feiShuUtils;

    @Override
    protected void handle(SendTaskDto sendTaskDto) {
        FeiShuApp feiShuApp = JSONUtil.toBean(sendTaskDto.getAppConfigJson(), FeiShuApp.class);
        String tenantAccessToken = feiShuUtils.getTenantAccessToken(feiShuApp);
        feiShuUtils.sendMessage(tenantAccessToken, sendTaskDto);
    }
}
