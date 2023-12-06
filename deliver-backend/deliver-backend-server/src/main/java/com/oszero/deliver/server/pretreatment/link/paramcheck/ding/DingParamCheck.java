package com.oszero.deliver.server.pretreatment.link.paramcheck.ding;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.constant.TraceIdConstant;
import com.oszero.deliver.server.exception.MessageException;
import com.oszero.deliver.server.log.MessageLinkTraceLogger;
import com.oszero.deliver.server.model.app.DingApp;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.pretreatment.link.BusinessLink;
import com.oszero.deliver.server.pretreatment.link.LinkContext;
import com.oszero.deliver.server.pretreatment.link.paramcheck.ParamStrategy;
import com.oszero.deliver.server.util.AesUtils;
import com.oszero.deliver.server.util.IpUtils;
import com.oszero.deliver.server.util.MDCUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 钉钉参数校验
 *
 * @author oszero
 * @version 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DingParamCheck implements BusinessLink<SendTaskDto> {

    private final Map<String, ParamStrategy> dingParamStrategyMap;
    private final AesUtils aesUtils;
    private final MessageLinkTraceLogger messageLinkTraceLogger;

    @Override
    public void process(LinkContext<SendTaskDto> context) {
        SendTaskDto sendTaskDto = context.getProcessModel();
        String dingDingApp = aesUtils.decrypt(sendTaskDto.getAppConfig());
        DingApp dingApp = JSONUtil.toBean(dingDingApp, DingApp.class);

        // 得到参数 Map
        Map<String, Object> paramMap = sendTaskDto.getParamMap();

        String pushSubject = paramMap.get("pushSubject").toString();
        String dingUserIdType = paramMap.get("dingUserIdType").toString();
        List<String> users = sendTaskDto.getUsers();

        // 封装通用参数
        if ("robot".equals(pushSubject)) {
            if ("openConversationId".equals(dingUserIdType)) {
                paramMap.put(dingUserIdType, users.get(0));
            } else {
                paramMap.put(dingUserIdType, users);
            }
            paramMap.put("robotCode", dingApp.getRobotCode());
        } else if ("workNotice".equals(pushSubject)) {
            paramMap.put(dingUserIdType, String.join(",", users));
            paramMap.put("agent_id", dingApp.getAgentId());
        } else {
            throw new MessageException("钉钉消息校验异常，不支持此推送主体！！！");
        }

        // 参数校验
        ParamStrategy paramStrategy = dingParamStrategyMap.get(ParamStrategy.DING_STRATEGY_BEAN_PRE_NAME + sendTaskDto.getMessageType());

        try {
            paramStrategy.paramCheck(sendTaskDto);
        } catch (Exception e) {
            log.error("[DingParamCheck#process]异常：{}", e.toString());
            throw new MessageException("钉钉消息参数校验失败，" + e.getMessage() + "！！！");
        }

        messageLinkTraceLogger.info("消息链路 ID: {}, 模板 ID: {}, 应用 ID: {}, 接收人列表: {}, 是否重试消息: {}, 重试次数剩余: {}, 请求 IP: {}, 处理信息: {}"
                , MDCUtils.get(TraceIdConstant.TRACE_ID)
                ,sendTaskDto.getTemplateId()
                ,sendTaskDto.getAppId()
                ,sendTaskDto.getUsers()
                ,sendTaskDto.getRetried()
                ,sendTaskDto.getRetry()
                , IpUtils.getClientIp()
                ,"钉钉参数校验成功");

    }
}
