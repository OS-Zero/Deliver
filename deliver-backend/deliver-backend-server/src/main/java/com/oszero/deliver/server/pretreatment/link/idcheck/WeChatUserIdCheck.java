package com.oszero.deliver.server.pretreatment.link.idcheck;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.model.app.WeChatApp;
import com.oszero.deliver.server.util.AesUtils;
import com.oszero.deliver.server.client.WeChatClient;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.pretreatment.link.BusinessLink;
import com.oszero.deliver.server.pretreatment.link.LinkContext;
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
public class WeChatUserIdCheck implements BusinessLink<SendTaskDto> {

    private final WeChatClient weChatClient;
    private final AesUtils aesUtils;

    @Override
    public void process(LinkContext<SendTaskDto> context) {
        SendTaskDto sendTaskDto = context.getProcessModel();
        Map<String, Object> paramMap = sendTaskDto.getParamMap();
        String wechatUserIdType = paramMap.get("wechatUserIdType").toString();
        if (!new HashSet<>(Arrays.asList("touser", "to_parent_userid", "to_student_userid")).contains(wechatUserIdType)) {
            return;
        }
        String appConfigJson = aesUtils.decrypt(sendTaskDto.getAppConfig());
        WeChatApp weChatApp = JSONUtil.toBean(appConfigJson, WeChatApp.class);
        List<String> users = sendTaskDto.getUsers();
        String accessToken = weChatClient.getAccessToken(weChatApp);
        weChatClient.checkUserId(accessToken, users);
    }
}
