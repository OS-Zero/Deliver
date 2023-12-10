package com.oszero.deliver.server.pretreatment.link.idcheck;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.client.wechat.WeChatClient;
import com.oszero.deliver.server.model.app.WeChatApp;
import com.oszero.deliver.server.model.dto.common.SendTaskDto;
import com.oszero.deliver.server.pretreatment.common.MessageLink;
import com.oszero.deliver.server.pretreatment.common.LinkContext;
import com.oszero.deliver.server.util.MessageLinkTraceUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * 企业微信 userId 检查
 *
 * @author oszero
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class WeChatUserIdCheck implements MessageLink<SendTaskDto> {

    private final WeChatClient weChatClient;

    @Override
    public void process(LinkContext<SendTaskDto> context) {
        SendTaskDto sendTaskDto = context.getProcessModel();
        Map<String, Object> paramMap = sendTaskDto.getParamMap();
        String wechatUserIdType = paramMap.get("wechatUserIdType").toString();
        if (!new HashSet<>(Arrays.asList("touser", "to_parent_userid", "to_student_userid")).contains(wechatUserIdType)) {
            return;
        }
        String appConfigJson = sendTaskDto.getAppConfig();
        WeChatApp weChatApp = JSONUtil.toBean(appConfigJson, WeChatApp.class);
        List<String> users = sendTaskDto.getUsers();
        String accessToken = weChatClient.getAccessToken(weChatApp, sendTaskDto);
        weChatClient.checkUserId(accessToken, users, sendTaskDto);

        MessageLinkTraceUtils.recordMessageLifecycleInfoLog(sendTaskDto, "完成企微 ID 检查");
    }
}
