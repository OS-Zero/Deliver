package com.oszero.deliver.server.pretreatment.link.paramcheck.feishu.strategy;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.enums.MessageTypeEnum;
import com.oszero.deliver.server.message.param.feishu.FeiShuShareChatParam;
import com.oszero.deliver.server.message.param.feishu.FeiShuTextParam;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.pretreatment.link.paramcheck.ParamStrategy;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component(ParamStrategy.FEI_SHU_STRATEGY_BEAN_PRE_NAME + "6-4")
public class ShareChatParamStrategy implements ParamStrategy {

    @Override
    public void paramCheck(SendTaskDto sendTaskDto) throws Exception {
        Map<String, Object> paramMap = sendTaskDto.getParamMap();
        paramMap.put("msg_type", MessageTypeEnum.FEI_SHU_SHARE_CHAT.getMsgType());
        String json = JSONUtil.toJsonStr(paramMap);
        JSONUtil.toBean(json, FeiShuShareChatParam.class);
        sendTaskDto.setParamJson(json);
    }
}
