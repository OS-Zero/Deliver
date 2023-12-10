package com.oszero.deliver.server.pretreatment.link.idcheck;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.client.DingClient;
import com.oszero.deliver.server.model.app.DingApp;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.pretreatment.link.MessageLink;
import com.oszero.deliver.server.pretreatment.link.LinkContext;
import com.oszero.deliver.server.util.MessageLinkTraceUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 钉钉 userId 检查
 *
 * @author oszero
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class DingUserIdCheck implements MessageLink<SendTaskDto> {

    private final DingClient dingClient;

    @Override
    public void process(LinkContext<SendTaskDto> context) {
        SendTaskDto sendTaskDto = context.getProcessModel();
        List<String> users = sendTaskDto.getUsers();
        Map<String, Object> paramMap = sendTaskDto.getParamMap();
        String dingUserIdType = paramMap.get("dingUserIdType").toString();
        // 这两者走 id 校验
        if ("userid_list".equals(dingUserIdType) || "userIds".equals(dingUserIdType)) {

            String appConfigJson = sendTaskDto.getAppConfig();
            DingApp dingApp = JSONUtil.toBean(appConfigJson, DingApp.class);
            String accessToken = dingClient.getAccessToken(dingApp, sendTaskDto);
            users.forEach(userId -> dingClient.checkId(accessToken, userId, sendTaskDto));

            MessageLinkTraceUtils.recordMessageLifecycleInfoLog(sendTaskDto, "完成钉钉 ID 检查");
        }
    }
}
