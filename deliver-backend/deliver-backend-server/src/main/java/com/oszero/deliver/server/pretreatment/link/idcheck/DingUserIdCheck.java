package com.oszero.deliver.server.pretreatment.link.idcheck;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.client.DingClient;
import com.oszero.deliver.server.constant.TraceIdConstant;
import com.oszero.deliver.server.log.MessageLinkTraceLogger;
import com.oszero.deliver.server.model.app.DingApp;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.pretreatment.link.BusinessLink;
import com.oszero.deliver.server.pretreatment.link.LinkContext;
import com.oszero.deliver.server.util.AesUtils;
import com.oszero.deliver.server.util.IpUtils;
import com.oszero.deliver.server.util.MDCUtils;
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

    private final DingClient dingClient;
    private final AesUtils aesUtils;
    private final MessageLinkTraceLogger messageLinkTraceLogger;

    @Override
    public void process(LinkContext<SendTaskDto> context) {
        SendTaskDto sendTaskDto = context.getProcessModel();
        List<String> users = sendTaskDto.getUsers();
        Map<String, Object> paramMap = sendTaskDto.getParamMap();
        String dingUserIdType = paramMap.get("dingUserIdType").toString();
        // 这两者走 id 校验
        if ("userid_list".equals(dingUserIdType) || "userIds".equals(dingUserIdType)) {

            String appConfigJson = aesUtils.decrypt(sendTaskDto.getAppConfig());
            DingApp dingApp = JSONUtil.toBean(appConfigJson, DingApp.class);
            String accessToken = dingClient.getAccessToken(dingApp);
            users.forEach(userId -> dingClient.checkId(accessToken, userId));
        }

        messageLinkTraceLogger.info("消息链路 ID: {}, 模板 ID: {}, 应用 ID: {}, 接收人列表: {}, 是否重试消息: {}, 重试次数剩余: {}, 请求 IP: {}, 处理信息: {}"
                , MDCUtils.get(TraceIdConstant.TRACE_ID)
                ,sendTaskDto.getTemplateId()
                ,sendTaskDto.getAppId()
                ,sendTaskDto.getUsers()
                ,sendTaskDto.getRetried()
                ,sendTaskDto.getRetry()
                , IpUtils.getClientIp()
                ,"钉钉 userId 检查成功");
    }
}
