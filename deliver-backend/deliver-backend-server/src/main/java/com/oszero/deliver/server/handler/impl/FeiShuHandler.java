package com.oszero.deliver.server.handler.impl;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.client.feishu.FeiShuClient;
import com.oszero.deliver.server.handler.BaseHandler;
import com.oszero.deliver.server.model.app.FeiShuApp;
import com.oszero.deliver.server.model.dto.common.SendTaskDto;
import com.oszero.deliver.server.web.service.MessageRecordService;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 飞书消费者处理器
 *
 * @author oszero
 * @version 1.0.0
 */
@Component
public class FeiShuHandler extends BaseHandler {

    private final FeiShuClient feiShuClient;

    /**
     * 可以批量发送的消息类型
     */
    private static final Set<String> BATCH_MESSAGE_TYPE =
            new HashSet<>(Arrays.asList("text", "image", "post", "share_chat", "interactive"));

    public FeiShuHandler(FeiShuClient feiShuClient, MessageRecordService messageRecordService) {
        this.feiShuClient = feiShuClient;
        this.messageRecordService = messageRecordService;
    }

    @Override
    protected void handle(SendTaskDto sendTaskDto) {
        String appConfigJson = sendTaskDto.getAppConfig();
        FeiShuApp feiShuApp = JSONUtil.toBean(appConfigJson, FeiShuApp.class);

        String tenantAccessToken = feiShuClient.getTenantAccessToken(feiShuApp, sendTaskDto);
        Map<String, Object> paramMap = sendTaskDto.getParamMap();
        String feiShuUserIdType = paramMap.get("feiShuUserIdType").toString();
        String msgType = paramMap.get("msg_type").toString();

        // 支持发送多种飞书的 usersId，包括 [用户(user_id),用户(email),群组(chat_id),部门(department_id)]
        if ("user_id".equals(feiShuUserIdType)) {

            // 如果满足批量发送的用户类型则批量发送
            if (BATCH_MESSAGE_TYPE.contains(msgType)) {
                feiShuClient.sendMessageBatch(tenantAccessToken, sendTaskDto);
            } else { // 否则单个发送
                feiShuClient.sendMessage(tenantAccessToken, sendTaskDto);
            }
        } else if ("email".equals(feiShuUserIdType)) {

            // 邮箱类型只支持单个发送
            feiShuClient.sendMessage(tenantAccessToken, sendTaskDto);
        } else if ("chat_id".equals((feiShuUserIdType))) {

            // 群聊类型只支持单个发送
            feiShuClient.sendMessage(tenantAccessToken, sendTaskDto);
        } else if ("department_id".equals((feiShuUserIdType))) {

            // 参数的处理
            Object user_ids = paramMap.get("user_ids");
            paramMap.remove("user_ids");
            paramMap.put("department_ids", user_ids);
            // 部门只能批量
            feiShuClient.sendMessageBatch(tenantAccessToken, sendTaskDto);
        }
    }
}
