package com.oszero.deliver.server.pretreatment.link.idcheck;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.model.app.DingApp;
import com.oszero.deliver.server.util.channel.DingUtils;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.pretreatment.link.BusinessLink;
import com.oszero.deliver.server.pretreatment.link.LinkContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DingUserIdCheck implements BusinessLink<SendTaskDto> {

    private final DingUtils dingUtils;

    @Override
    public void process(LinkContext<SendTaskDto> context) {
        SendTaskDto sendTaskDto = context.getProcessModel();
        List<String> users = sendTaskDto.getUsers();

        String appConfigJson = sendTaskDto.getAppConfigJson();
        DingApp dingApp = JSONUtil.toBean(appConfigJson, DingApp.class);
        String accessToken = dingUtils.getAccessToken(dingApp);
        users.forEach(userId -> dingUtils.checkId(accessToken, userId));
    }
}
