package com.oszero.deliver.server.pretreatment.link.idcheck;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.cache.manager.ServerCacheManager;
import com.oszero.deliver.server.client.feishu.FeiShuClient;
import com.oszero.deliver.server.model.app.FeiShuApp;
import com.oszero.deliver.server.model.dto.common.SendTaskDto;
import com.oszero.deliver.server.pretreatment.common.MessageLink;
import com.oszero.deliver.server.pretreatment.common.LinkContext;
import com.oszero.deliver.server.util.MessageLinkTraceUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 飞书 userId 检查
 *
 * @author oszero
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class FeiShuUserIdCheck implements MessageLink<SendTaskDto> {

    private final FeiShuClient feiShuClient;
    private final ServerCacheManager serverCacheManager;

    @Override
    public void process(LinkContext<SendTaskDto> context) {
        SendTaskDto sendTaskDto = context.getProcessModel();
        Map<String, Object> paramMap = sendTaskDto.getParamMap();
        String feiShuUserIdType = paramMap.get("feiShuUserIdType").toString();
        if (!"user_id".equals(feiShuUserIdType)) {
            return;
        }
        String appConfigJson = sendTaskDto.getAppConfig();
        FeiShuApp feiShuApp = JSONUtil.toBean(appConfigJson, FeiShuApp.class);
        List<String> users = sendTaskDto.getUsers();
        String tenantAccessToken = serverCacheManager.getFeiShuToken(feiShuApp, sendTaskDto);
        users.forEach(userId -> {
            feiShuClient.checkUserId(tenantAccessToken, userId, sendTaskDto);
        });
        MessageLinkTraceUtils.recordMessageLifecycleInfoLog(sendTaskDto, "完成飞书 ID 检查");
    }
}
