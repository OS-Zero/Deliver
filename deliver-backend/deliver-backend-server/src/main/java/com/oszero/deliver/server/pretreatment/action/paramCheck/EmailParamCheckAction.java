package com.oszero.deliver.server.pretreatment.action.paramCheck;

import cn.dianhun.om.message.constant.EmailParamConstant;
import cn.dianhun.om.message.exception.PipelineProcessException;
import cn.dianhun.om.message.model.dto.SendTaskDto;
import cn.dianhun.om.message.pipeline.BusinessProcess;
import cn.dianhun.om.message.pipeline.ProcessContext;
import cn.dianhun.om.message.util.CommonUtil;
import cn.hutool.json.JSONUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author zbzbzzz
 * @email zbzbzzz@dianhun.cn
 * @date 2023/4/20 10:05
 */

@Service
public class EmailParamCheckAction implements BusinessProcess<SendTaskDto> {
    @Override
    public void process(ProcessContext<SendTaskDto> context) {
        Map<String, String> paramMap = context.getProcessModel().getParamMap();
        CommonUtil.paramCheckAndThrow(paramMap, EmailParamConstant.CONTENT);
        CommonUtil.paramCheckAndDefault(paramMap, EmailParamConstant.SENDER, EmailParamConstant.DEFAULT_SENDER);
        CommonUtil.paramCheckAndThrow(paramMap, EmailParamConstant.SUBJECT);
        CommonUtil.paramCheckAndDefault(paramMap, EmailParamConstant.TOCC, EmailParamConstant.DEFAULT_TOCC);
        CommonUtil.paramCheckAndDefault(paramMap, EmailParamConstant.TOBCC, EmailParamConstant.DEFAULT_TOBCC);
        CommonUtil.paramCheckAndDefault(paramMap, EmailParamConstant.USERNAME, EmailParamConstant.DEFAULT_USERNAME);

        if (!JSONUtil.isTypeJSONArray(paramMap.get(EmailParamConstant.TOCC))) {
            throw new PipelineProcessException("抄送者列表不符合JsonArray定义！");
        }
        if (!JSONUtil.isTypeJSONArray(paramMap.get(EmailParamConstant.TOBCC))) {
            throw new PipelineProcessException("密送者列表不符合JsonArray定义！");
        }
    }

}
