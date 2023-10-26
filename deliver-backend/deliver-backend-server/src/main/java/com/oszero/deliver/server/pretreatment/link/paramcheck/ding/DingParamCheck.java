package com.oszero.deliver.server.pretreatment.link.paramcheck.ding;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.exception.LinkProcessException;
import com.oszero.deliver.server.model.app.DingApp;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.pretreatment.link.BusinessLink;
import com.oszero.deliver.server.pretreatment.link.LinkContext;
import com.oszero.deliver.server.pretreatment.link.paramcheck.ParamStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class DingParamCheck implements BusinessLink<SendTaskDto> {

    private final Map<String, ParamStrategy> dingDingParamStrategyMap;

    @Override
    public void process(LinkContext<SendTaskDto> context) {
        SendTaskDto sendTaskDto = context.getProcessModel();
        String messageType = sendTaskDto.getMessageType();
        String dingDingApp = sendTaskDto.getAppConfigJson();
        DingApp dingApp = JSONUtil.toBean(dingDingApp, DingApp.class);
        Map<String, Object> paramMap = sendTaskDto.getParamMap();

        String s = sendTaskDto.getUsers().toString();
        String substring = s.substring(1, s.length() - 1);
        paramMap.put("userid_list",substring);
        paramMap.put("agent_id",dingApp.getAgentId());

        ParamStrategy paramStrategy = dingDingParamStrategyMap.get(ParamStrategy.DING_STRATEGY_BEAN_PRE_NAME + sendTaskDto.getMessageType());

        try {
            paramStrategy.paramCheck(sendTaskDto);
        }catch (Exception e){
            throw  new LinkProcessException("钉钉 文本消息 校验异常！！！");
        }

    }
}
