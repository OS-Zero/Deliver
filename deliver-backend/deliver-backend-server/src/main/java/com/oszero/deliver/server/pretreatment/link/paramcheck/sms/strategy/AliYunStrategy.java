package com.oszero.deliver.server.pretreatment.link.paramcheck.sms.strategy;

import com.oszero.deliver.server.model.dto.common.SendTaskDto;
import com.oszero.deliver.server.pretreatment.link.paramcheck.ParamStrategy;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 短信阿里云参数校验
 *
 * @author oszero
 * @version 1.0.0
 */
@Component(ParamStrategy.SMS_STRATEGY_BEAN_PRE_NAME + "aliyun")
public class AliYunStrategy implements ParamStrategy {
    @Override
    public void paramCheck(SendTaskDto sendTaskDto) throws Exception {
        Map<String, Object> paramMap = sendTaskDto.getParamMap();

    }
}
