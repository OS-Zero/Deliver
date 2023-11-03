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

import java.util.HashMap;
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
        Map<String, Object> paramMap = sendTaskDto.getParamMap();

        String s = sendTaskDto.getUsers().toString();
        String substring = s.substring(1, s.length() - 1);

        // 钉钉 userid_list、agent_id 在请求参数里
        paramMap.put("userid_list", substring);
        paramMap.put("agent_id", dingApp.getAgentId());

        ParamStrategy paramStrategy = dingParamStrategyMap.get(ParamStrategy.DING_STRATEGY_BEAN_PRE_NAME + sendTaskDto.getMessageType());

        try {
            paramStrategy.paramCheck(sendTaskDto);
        } catch (Exception e) {
            log.error("[DingParamCheck#process]异常：{}", e.toString());
            throw new LinkProcessException("钉钉消息校验异常！！！");
        }

    }
}
