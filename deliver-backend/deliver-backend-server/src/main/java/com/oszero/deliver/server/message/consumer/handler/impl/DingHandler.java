package com.oszero.deliver.server.message.consumer.handler.impl;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.log.MessageLinkTraceLogger;
import com.oszero.deliver.server.message.consumer.handler.BaseHandler;
import com.oszero.deliver.server.model.app.DingApp;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.util.AesUtils;
import com.oszero.deliver.server.client.DingClient;
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
    private final AesUtils aesUtils;

    public DingHandler(DingClient dingClient, MessageRecordService messageRecordService, AesUtils aesUtils, MessageLinkTraceLogger messageLinkTraceLogger) {
        this.dingClient = dingClient;
        this.messageRecordService = messageRecordService;
        this.aesUtils = aesUtils;
        this.messageLinkTraceLogger = messageLinkTraceLogger;
    }

    @Override
    protected void handle(SendTaskDto sendTaskDto) throws Exception {
        String appConfigJson = aesUtils.decrypt(sendTaskDto.getAppConfig());
        DingApp dingApp = JSONUtil.toBean(appConfigJson, DingApp.class);
        String accessToken = dingClient.getAccessToken(dingApp);

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
