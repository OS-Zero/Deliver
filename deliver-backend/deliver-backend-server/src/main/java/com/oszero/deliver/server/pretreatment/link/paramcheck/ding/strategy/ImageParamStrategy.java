package com.oszero.deliver.server.pretreatment.link.paramcheck.ding.strategy;

import com.oszero.deliver.server.enums.MessageTypeEnum;
import com.oszero.deliver.server.model.dto.common.SendTaskDto;
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
        String pushSubject = paramMap.get("pushSubject").toString();
        if ("robot".equals(pushSubject)) {
            paramMap.put("msgKey", "sampleImageMsg");
        } else {
            Map<String, Object> msg = (Map<String, Object>) paramMap.get("msg");
            msg.put("msgtype", MessageTypeEnum.DING_IMAGE.getMsgType());
        }

        // TODO:参数校验
    }
}
