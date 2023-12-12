package com.oszero.deliver.server.handler.impl;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.cache.manager.ServerCacheManager;
import com.oszero.deliver.server.client.ding.DingClient;
import com.oszero.deliver.server.handler.BaseHandler;
import com.oszero.deliver.server.model.app.DingApp;
import com.oszero.deliver.server.model.dto.common.SendTaskDto;
import com.oszero.deliver.server.web.service.MessageRecordService;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 钉钉消费者处理器
 *
 * @author oszero
 * @version 1.0.0
 */
@Component
public class DingHandler extends BaseHandler {

    private final DingClient dingClient;
    private final ServerCacheManager serverCacheManager;

    public DingHandler(DingClient dingClient, ServerCacheManager serverCacheManager, MessageRecordService messageRecordService) {
        this.dingClient = dingClient;
        this.serverCacheManager = serverCacheManager;
        this.messageRecordService = messageRecordService;
    }

    @Override
    protected void handle(SendTaskDto sendTaskDto) throws Exception {
        String appConfigJson = sendTaskDto.getAppConfig();
        DingApp dingApp = JSONUtil.toBean(appConfigJson, DingApp.class);
        String accessToken = serverCacheManager.getDingToken(dingApp, sendTaskDto);

        Map<String, Object> paramMap = sendTaskDto.getParamMap();
        String pushSubject = paramMap.get("pushSubject").toString();
        // 删除掉一些标识信息
        paramMap.remove("dingUserIdType");
        paramMap.remove("pushSubject");
        if ("robot".equals(pushSubject)) {
            Object msgParam = paramMap.get("msgParam");
            paramMap.put("msgParam", JSONUtil.toJsonStr(msgParam));
            dingClient.sendRobotMessage(accessToken, sendTaskDto);
        } else {
            dingClient.sendWorkNoticeMessage(accessToken, sendTaskDto);
        }
    }
}
