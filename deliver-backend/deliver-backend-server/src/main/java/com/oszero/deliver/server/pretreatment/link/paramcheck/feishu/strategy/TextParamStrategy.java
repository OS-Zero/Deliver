package com.oszero.deliver.server.pretreatment.link.paramcheck.feishu.strategy;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.enums.MessageTypeEnum;
import com.oszero.deliver.server.message.param.feishu.FeiShuTextParam;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.pretreatment.link.paramcheck.ParamStrategy;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 飞书文本消息参数校验策略
 *
 * @author oszero
 * @version 1.0.0
 */
@Component(ParamStrategy.FEI_SHU_STRATEGY_BEAN_PRE_NAME + "1")
public class TextParamStrategy implements ParamStrategy {

    @Override
    public void paramCheck(SendTaskDto sendTaskDto) throws Exception {
        Map<String, Object> paramMap = sendTaskDto.getParamMap();
        paramMap.put("msg_type", MessageTypeEnum.TEXT.getMsgType());
        String json = JSONUtil.toJsonStr(paramMap);
        JSONUtil.toBean(json, FeiShuTextParam.class);
        sendTaskDto.setParamJson(json);
    }
}
