package com.oszero.deliver.server.message.consumer.handler.impl;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.exception.MessageException;
import com.oszero.deliver.server.message.consumer.handler.BaseHandler;
import com.oszero.deliver.server.model.app.FeiShuApp;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.util.AesUtils;
import com.oszero.deliver.server.util.channel.FeiShuUtils;
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

    private final FeiShuUtils feiShuUtils;
    private final AesUtils aesUtils;

    /**
     * 可以批量发送的消息类型
     */
    private static final Set<String> BATCH_MESSAGE_TYPE =
            new HashSet<>(Arrays.asList("text", "image", "post", "share_chat", "interactive"));

    public FeiShuHandler(FeiShuUtils feiShuUtils, MessageRecordService messageRecordService, AesUtils aesUtils) {
        this.feiShuUtils = feiShuUtils;
        this.messageRecordService = messageRecordService;
        this.aesUtils = aesUtils;
    }

    @Override
    protected void handle(SendTaskDto sendTaskDto) {
        String appConfigJson = aesUtils.decrypt(sendTaskDto.getAppConfig());
        FeiShuApp feiShuApp = JSONUtil.toBean(appConfigJson, FeiShuApp.class);

        String tenantAccessToken = feiShuUtils.getTenantAccessToken(feiShuApp);
        Map<String, Object> paramMap = sendTaskDto.getParamMap();
        String feiShuUserIdType = paramMap.get("feiShuUserIdType").toString();
        String msgType = paramMap.get("msg_type").toString();

        // 支持发送多种飞书的 usersId，包括 [用户(user_id),用户(email),群组(chat_id),部门(department_id)]
        if ("user_id".equals(feiShuUserIdType)) {

            // 如果满足批量发送的用户类型则批量发送
            if (BATCH_MESSAGE_TYPE.contains(msgType)) {
                feiShuUtils.sendMessageBatch(tenantAccessToken, sendTaskDto);
            } else { // 否则单个发送
                feiShuUtils.sendMessage(tenantAccessToken, sendTaskDto);
            }
        } else if ("email".equals(feiShuUserIdType)) {

            // 邮箱类型只支持单个发送
            feiShuUtils.sendMessage(tenantAccessToken, sendTaskDto);
        } else if ("chat_id".equals((feiShuUserIdType))) {

            // 群聊类型只支持单个发送
            feiShuUtils.sendMessage(tenantAccessToken, sendTaskDto);
        } else if ("department_id".equals((feiShuUserIdType))) {

            // 不能批量的消息类型但传递了部门 ID 则直接抛出异常
            if (!BATCH_MESSAGE_TYPE.contains(msgType)) {
                throw new MessageException("部门 ID 类型，不支持发送此类型(" + msgType + ")消息！！！");
            }
            // 参数的处理
            Object user_ids = paramMap.get("user_ids");
            paramMap.remove("user_ids");
            paramMap.put("department_ids", user_ids);
            // 部门只能批量
            feiShuUtils.sendMessageBatch(tenantAccessToken, sendTaskDto);
        } else {
            throw new MessageException("此类型(" + feiShuUserIdType + ")平台 ID 不支持！！！");
        }
    }
}
