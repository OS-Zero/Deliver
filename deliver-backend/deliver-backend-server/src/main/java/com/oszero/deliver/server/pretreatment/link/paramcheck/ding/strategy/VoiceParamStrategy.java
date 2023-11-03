package com.oszero.deliver.server.pretreatment.link.paramcheck.ding.strategy;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.enums.MessageTypeEnum;
import com.oszero.deliver.server.message.param.ding.DingTextParam;
import com.oszero.deliver.server.message.param.ding.DingVoiceParam;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.pretreatment.link.paramcheck.ParamStrategy;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 钉钉语言消息参数校验策略
 *
 * @author oszero
 * @version 1.0.0
 */
@Component(ParamStrategy.DING_STRATEGY_BEAN_PRE_NAME + "4-2")
public class VoiceParamStrategy implements ParamStrategy {

    @Override
    public void paramCheck(SendTaskDto sendTaskDto) throws Exception {
        Map<String, Object> paramMap = sendTaskDto.getParamMap();
        String msg = JSONUtil.toJsonStr(paramMap.get("msg"));
        DingVoiceParam.VoiceMessage voiceMessage = JSONUtil.toBean(msg, DingVoiceParam.VoiceMessage.class);
        voiceMessage.setMsgtype(MessageTypeEnum.DING_VOICE.getMsgType());
        paramMap.put("msg", voiceMessage);
        String json = JSONUtil.toJsonStr(paramMap);
        JSONUtil.toBean(json, DingVoiceParam.class);
        sendTaskDto.setParamJson(json);
    }
}
