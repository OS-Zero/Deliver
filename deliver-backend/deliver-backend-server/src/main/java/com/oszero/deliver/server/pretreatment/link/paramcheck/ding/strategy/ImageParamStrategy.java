package com.oszero.deliver.server.pretreatment.link.paramcheck.ding.strategy;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.enums.MessageTypeEnum;
import com.oszero.deliver.server.message.param.ding.DingImageParam;
import com.oszero.deliver.server.message.param.ding.DingTextParam;
import com.oszero.deliver.server.message.param.ding.DingVoiceParam;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.pretreatment.link.paramcheck.ParamStrategy;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 钉钉图片消息参数校验策略
 *
 * @author oszero
 * @version 1.0.0
 */
@Component(ParamStrategy.DING_STRATEGY_BEAN_PRE_NAME + "4-1")
public class ImageParamStrategy implements ParamStrategy {

    @Override
    public void paramCheck(SendTaskDto sendTaskDto) throws Exception {
        Map<String, Object> paramMap = sendTaskDto.getParamMap();
        String msg = JSONUtil.toJsonStr(paramMap.get("msg"));
        DingImageParam.ImageMessage imageMessage = JSONUtil.toBean(msg, DingImageParam.ImageMessage.class);
        imageMessage.setMsgtype(MessageTypeEnum.DING_IMAGE.getMsgType());
        paramMap.put("msg", imageMessage);
        String json = JSONUtil.toJsonStr(paramMap);
        JSONUtil.toBean(json, DingImageParam.class);
        sendTaskDto.setParamJson(json);
    }
}
