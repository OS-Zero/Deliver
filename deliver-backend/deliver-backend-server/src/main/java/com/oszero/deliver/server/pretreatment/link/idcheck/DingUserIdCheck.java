package com.oszero.deliver.server.pretreatment.link.idcheck;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.model.app.DingApp;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.pretreatment.link.BusinessLink;
import com.oszero.deliver.server.pretreatment.link.LinkContext;
import com.oszero.deliver.server.util.channel.DingUtils;
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
public class DingUserIdCheck implements BusinessLink<SendTaskDto> {

    private final DingUtils dingUtils;

    @Override
    public void process(LinkContext<SendTaskDto> context) {
        SendTaskDto sendTaskDto = context.getProcessModel();
        List<String> users = sendTaskDto.getUsers();
        Map<String, Object> paramMap = sendTaskDto.getParamMap();
        String dingUserIdType = paramMap.get("dingUserIdType").toString();
        // 这两者走 id 校验
        if ("userid_list".equals(dingUserIdType) || "userIds".equals(dingUserIdType)) {
            String appConfigJson = sendTaskDto.getAppConfigJson();
            DingApp dingApp = JSONUtil.toBean(appConfigJson, DingApp.class);
            String accessToken = dingUtils.getAccessToken(dingApp);
            users.forEach(userId -> dingUtils.checkId(accessToken, userId));
        }
    }
}
