package com.oszero.deliver.server.pretreatment.link.paramcheck;

import com.oszero.deliver.server.model.dto.SendTaskDto;

public interface ParamStrategy {
    String CALL_STRATEGY_BEAN_PRE_NAME = "call-";
    String SMS_STRATEGY_BEAN_PRE_NAME = "sms-";
    String MAIL_STRATEGY_BEAN_PRE_NAME = "mail-";
    String DING_STRATEGY_BEAN_PRE_NAME = "ding-";
    String WECHAT_STRATEGY_BEAN_PRE_NAME = "wechat-";
    String FEI_SHU_STRATEGY_BEAN_PRE_NAME = "feiShu-";

    void paramCheck(SendTaskDto sendTaskDto) throws Exception;
}
