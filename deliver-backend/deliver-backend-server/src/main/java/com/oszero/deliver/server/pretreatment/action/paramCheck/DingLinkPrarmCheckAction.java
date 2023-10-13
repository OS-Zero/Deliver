package com.oszero.deliver.server.pretreatment.action.paramCheck;

import cn.dianhun.om.message.constant.DingParamConstant;
import cn.dianhun.om.message.model.dto.SendTaskDto;
import cn.dianhun.om.message.pipeline.BusinessProcess;
import cn.dianhun.om.message.pipeline.ProcessContext;
import cn.dianhun.om.message.util.CommonUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author zbzbzzz
 * @email zbzbzzz@dianhun.cn
 * @date 2023/4/26 15:16
 */

@Service
public class DingLinkPrarmCheckAction implements BusinessProcess<SendTaskDto> {
    @Override
    public void process(ProcessContext<SendTaskDto> context) {
        Map<String, String> paramMap = context.getProcessModel().getParamMap();
        CommonUtil.paramCheckAndThrow(paramMap, DingParamConstant.LINK_TEXT);
        CommonUtil.paramCheckAndThrow(paramMap, DingParamConstant.LINK_TITLE);
        CommonUtil.paramCheckAndThrow(paramMap, DingParamConstant.LINK_PICURL);
        CommonUtil.paramCheckAndThrow(paramMap, DingParamConstant.LINK_MSGURL);
    }
}
