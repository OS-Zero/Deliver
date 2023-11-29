package com.oszero.deliver.server.pretreatment.link.idcheck;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.model.app.FeiShuApp;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.pretreatment.link.BusinessLink;
import com.oszero.deliver.server.pretreatment.link.LinkContext;
import com.oszero.deliver.server.util.AesUtils;
import com.oszero.deliver.server.util.channel.FeiShuUtils;
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
public class FeiShuUserIdCheck implements BusinessLink<SendTaskDto> {

    private final FeiShuUtils feiShuUtils;
    private final AesUtils aesUtils;

    @Override
    public void process(LinkContext<SendTaskDto> context) {
        SendTaskDto sendTaskDto = context.getProcessModel();
        Map<String, Object> paramMap = sendTaskDto.getParamMap();
        String feiShuUserIdType = paramMap.get("feiShuUserIdType").toString();
        if (!"user_id".equals(feiShuUserIdType)) {
            return;
        }
        String appConfigJson = aesUtils.decrypt(sendTaskDto.getAppConfig());
        FeiShuApp feiShuApp = JSONUtil.toBean(appConfigJson, FeiShuApp.class);
        List<String> users = sendTaskDto.getUsers();
        users.forEach(userId -> {
            String tenantAccessToken = feiShuUtils.getTenantAccessToken(feiShuApp);
            feiShuUtils.checkUserId(tenantAccessToken, userId);
        });
    }
}
