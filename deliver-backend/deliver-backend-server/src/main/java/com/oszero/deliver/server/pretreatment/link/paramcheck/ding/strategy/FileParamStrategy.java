package com.oszero.deliver.server.pretreatment.link.paramcheck.ding.strategy;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.enums.MessageTypeEnum;
import com.oszero.deliver.server.message.param.ding.DingFileParam;
import com.oszero.deliver.server.message.param.ding.DingTextParam;
import com.oszero.deliver.server.message.param.ding.DingVoiceParam;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.pretreatment.link.paramcheck.ParamStrategy;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 钉钉文件消息参数校验策略
 *
 * @author oszero
 * @version 1.0.0
 */
@Component(ParamStrategy.DING_STRATEGY_BEAN_PRE_NAME + "4-3")
public class FileParamStrategy implements ParamStrategy {


    @Override
    public void paramCheck(SendTaskDto sendTaskDto) throws Exception {
        Map<String, Object> paramMap = sendTaskDto.getParamMap();
        String msg = JSONUtil.toJsonStr(paramMap.get("msg"));
        DingFileParam.FileMessage  fileMessage = JSONUtil.toBean(msg, DingFileParam.FileMessage.class);
        fileMessage.setMsgtype(MessageTypeEnum.DING_FILE.getMsgType());
        paramMap.put("msg", fileMessage);
        String json = JSONUtil.toJsonStr(paramMap);
        JSONUtil.toBean(json, DingFileParam.class);
        sendTaskDto.setParamJson(json);
    }
}
