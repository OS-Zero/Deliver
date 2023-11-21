package com.oszero.deliver.server.pretreatment.link.paramcheck.ding.strategy;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.enums.MessageTypeEnum;
import com.oszero.deliver.server.message.param.ding.DingCardParam;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.pretreatment.link.paramcheck.ParamStrategy;
import org.springframework.stereotype.Component;

import java.util.Map;


/**
 * 钉钉卡片消息参数校验策略
 *
 * @author oszero
 * @version 1.0.0
 */
@Component(ParamStrategy.DING_STRATEGY_BEAN_PRE_NAME + "4-7")
public class CardParamStrategy implements ParamStrategy {
    @Override
    public void paramCheck(SendTaskDto sendTaskDto) throws Exception {
        Map<String, Object> paramMap = sendTaskDto.getParamMap();
        String msg = JSONUtil.toJsonStr(paramMap.get("msg"));
        DingCardParam.DingCardMessage  dingCardMessage = JSONUtil.toBean(msg, DingCardParam.DingCardMessage.class);
        dingCardMessage.setMsgtype(MessageTypeEnum.DING_CARD.getMsgType());
        paramMap.put("msg", dingCardMessage);
        String json = JSONUtil.toJsonStr(paramMap);
        JSONUtil.toBean(json, DingCardParam.class);
        sendTaskDto.setParamJson(json);
    }
}
