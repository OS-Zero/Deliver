package com.oszero.deliver.server.pretreatment.link.paramcheck.ding;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.exception.LinkProcessException;
import com.oszero.deliver.server.model.app.DingApp;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.pretreatment.link.BusinessLink;
import com.oszero.deliver.server.pretreatment.link.LinkContext;
import com.oszero.deliver.server.pretreatment.link.paramcheck.ParamStrategy;
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

    @Override
    public void process(LinkContext<SendTaskDto> context) {
        SendTaskDto sendTaskDto = context.getProcessModel();
        String dingDingApp = sendTaskDto.getAppConfigJson();
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
            throw new LinkProcessException("钉钉消息校验异常，不支持此推送主体！！！");
        }

        // 参数校验
        ParamStrategy paramStrategy = dingParamStrategyMap.get(ParamStrategy.DING_STRATEGY_BEAN_PRE_NAME + sendTaskDto.getMessageType());

        try {
            paramStrategy.paramCheck(sendTaskDto);
        } catch (Exception e) {
            log.error("[DingParamCheck#process]异常：{}", e.toString());
            throw new LinkProcessException("钉钉消息校验异常！！！");
        }

    }
}
