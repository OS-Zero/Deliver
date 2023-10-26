package com.oszero.deliver.server.pretreatment.link.idcheck;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.exception.LinkProcessException;
import com.oszero.deliver.server.model.app.DingApp;
import com.oszero.deliver.server.util.channel.DingUtils;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.pretreatment.link.BusinessLink;
import com.oszero.deliver.server.pretreatment.link.LinkContext;
import com.taobao.api.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.naming.LinkException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DingUserIdCheck implements BusinessLink<SendTaskDto> {

    private final DingUtils dingUtils;

    @Override
    public void process(LinkContext<SendTaskDto> context) {
        SendTaskDto sendTaskDto = context.getProcessModel();
        List<String> users = sendTaskDto.getUsers();
        if (CollUtil.isEmpty(users)) {
            throw new LinkProcessException("钉钉用户 userId 校验异常,用户列表为空");
        }

        String appConfigJson = sendTaskDto.getAppConfigJson();
        DingApp dingApp = JSONUtil.toBean(appConfigJson, DingApp.class);
        String accessToken = dingUtils.getAccessToken(dingApp);


        users.stream().forEach(userId -> {
            dingUtils.checkId(accessToken, userId);
        });
    }
}
