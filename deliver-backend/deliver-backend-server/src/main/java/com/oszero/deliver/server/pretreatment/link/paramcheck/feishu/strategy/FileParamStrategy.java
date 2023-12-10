package com.oszero.deliver.server.pretreatment.link.paramcheck.feishu.strategy;

import com.oszero.deliver.server.enums.MessageTypeEnum;
import com.oszero.deliver.server.model.dto.common.SendTaskDto;
import com.oszero.deliver.server.pretreatment.link.paramcheck.ParamStrategy;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 飞书文件消息参数校验策略
 *
 * @author oszero
 * @version 1.0.0
 */
@Component(ParamStrategy.FEI_SHU_STRATEGY_BEAN_PRE_NAME + "6-8")
public class FileParamStrategy implements ParamStrategy {

    @Override
    public void paramCheck(SendTaskDto sendTaskDto) throws Exception {
        Map<String, Object> paramMap = sendTaskDto.getParamMap();
        paramMap.put("msg_type", MessageTypeEnum.FEI_SHU_FILE.getMsgType());

        // TODO:参数校验
    }
}
