package com.oszero.deliver.server.pretreatment.link.idcheck;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.model.app.FeiShuApp;
import com.oszero.deliver.server.util.channel.FeiShuUtils;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.pretreatment.link.BusinessLink;
import com.oszero.deliver.server.pretreatment.link.LinkContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeiShuUserIdCheck implements BusinessLink<SendTaskDto> {

    private final FeiShuUtils feiShuUtils;

    @Override
    public void process(LinkContext<SendTaskDto> context) {
        SendTaskDto sendTaskDto = context.getProcessModel();
        String appConfigJson = sendTaskDto.getAppConfigJson();
        FeiShuApp feiShuApp = JSONUtil.toBean(appConfigJson, FeiShuApp.class);
        List<String> users = sendTaskDto.getUsers();
        users.forEach(userId -> {
            String tenantAccessToken = feiShuUtils.getTenantAccessToken(feiShuApp);
            feiShuUtils.checkUserId(tenantAccessToken, userId);
        });
    }
}
