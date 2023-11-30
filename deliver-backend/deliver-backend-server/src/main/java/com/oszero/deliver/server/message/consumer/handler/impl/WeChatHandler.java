package com.oszero.deliver.server.message.consumer.handler.impl;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.message.consumer.handler.BaseHandler;
import com.oszero.deliver.server.model.app.WeChatApp;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.util.AesUtils;
import com.oszero.deliver.server.util.channel.WeChatUtils;
import com.oszero.deliver.server.web.service.MessageRecordService;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

/**
 * 企业微信消费者处理器
 *
 * @author oszero
 * @version 1.0.0
 */
@Component
public class WeChatHandler extends BaseHandler {

    private final WeChatUtils weChatUtils;
    private final AesUtils aesUtils;

    public WeChatHandler(WeChatUtils weChatUtils, MessageRecordService messageRecordService, AesUtils aesUtils) {
        this.weChatUtils = weChatUtils;
        this.messageRecordService = messageRecordService;
        this.aesUtils = aesUtils;
    }

    @Override
    protected void handle(SendTaskDto sendTaskDto) throws Exception {
        String appConfig = aesUtils.decrypt(sendTaskDto.getAppConfig());
        WeChatApp weChatApp = JSONUtil.toBean(appConfig, WeChatApp.class);

        String accessToken = weChatUtils.getAccessToken(weChatApp);

        Map<String, Object> paramMap = sendTaskDto.getParamMap();
        String pushSubject = paramMap.get("pushSubject").toString();
        String wechatUserIdType = paramMap.get("wechatUserIdType").toString();

        if ("app".equals(wechatUserIdType)) {
            if (new HashSet<>(Arrays.asList("touser", "toparty", "totag")).contains(wechatUserIdType)) {
                weChatUtils.sendAppMessage(accessToken, sendTaskDto);
            } else if (new HashSet<>(Arrays.asList("to_parent_userid", "to_student_userid", "to_party", "toall")).contains(wechatUserIdType)) {
                weChatUtils.sendAppSchoolMessage(accessToken, sendTaskDto);
            } else {
                weChatUtils.sendAppGroupMessage(accessToken, sendTaskDto);
            }
        } else if ("robot".equals(pushSubject)) {
            weChatUtils.sendRobotMessage(accessToken, sendTaskDto);
        }
        // 删除掉标识参数
        paramMap.remove("pushSubject");
        paramMap.remove("wechatUserIdType");
    }
}
