package com.oszero.deliver.server.pretreatment.link.paramcheck.wechat.strategy;

import com.oszero.deliver.server.enums.MessageTypeEnum;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.pretreatment.link.paramcheck.ParamStrategy;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 企微文本消息校验
 *
 * @author oszero
 * @version 1.0.0
 */
@Component(ParamStrategy.WECHAT_STRATEGY_BEAN_PRE_NAME + "1")
public class TextParamStrategy implements ParamStrategy {

    /**
     * 校验
     *
     * @param sendTaskDto dto
     * @throws Exception 异常
     */
    @Override
    public void paramCheck(SendTaskDto sendTaskDto) throws Exception {
        Map<String, Object> paramMap = sendTaskDto.getParamMap();
        paramMap.put("msgtype", MessageTypeEnum.TEXT.getMsgType());
    }
}
