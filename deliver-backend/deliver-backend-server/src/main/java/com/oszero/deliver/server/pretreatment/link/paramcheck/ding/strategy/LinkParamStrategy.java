package com.oszero.deliver.server.pretreatment.link.paramcheck.ding.strategy;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.enums.MessageTypeEnum;
import com.oszero.deliver.server.message.param.ding.DingLinkParam;
import com.oszero.deliver.server.message.param.ding.DingTextParam;
import com.oszero.deliver.server.message.param.ding.DingVoiceParam;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.pretreatment.link.paramcheck.ParamStrategy;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 钉钉链接消息参数校验策略
 *
 * @author oszero
 * @version 1.0.0
 */
@Component(ParamStrategy.DING_STRATEGY_BEAN_PRE_NAME + "4-4")
public class LinkParamStrategy implements ParamStrategy {

    @Override
    public void paramCheck(SendTaskDto sendTaskDto) throws Exception {
        Map<String, Object> paramMap = sendTaskDto.getParamMap();
        String msg = JSONUtil.toJsonStr(paramMap.get("msg"));
        DingLinkParam.LinkMessage linkMessage = JSONUtil.toBean(msg, DingLinkParam.LinkMessage.class);
        linkMessage.setMsgtype(MessageTypeEnum.DING_LINK.getMsgType());
        paramMap.put("msg", linkMessage);
        String json = JSONUtil.toJsonStr(paramMap);
        JSONUtil.toBean(json, DingTextParam.class);
        sendTaskDto.setParamJson(json);
    }
}
