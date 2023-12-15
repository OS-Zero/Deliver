package com.oszero.deliver.server.pretreatment.link.paramcheck.phone.strategy;

import com.oszero.deliver.server.model.dto.common.SendTaskDto;
import com.oszero.deliver.server.pretreatment.link.paramcheck.ParamStrategy;
import org.springframework.stereotype.Component;

/**
 * 语音电话腾讯云参数校验
 *
 * @author oszero
 * @version 1.0.0
 */
@Component(ParamStrategy.CALL_STRATEGY_BEAN_PRE_NAME + "tencent")
public class TencentStrategy implements ParamStrategy {
    @Override
    public void paramCheck(SendTaskDto sendTaskDto) throws Exception {

    }
}
