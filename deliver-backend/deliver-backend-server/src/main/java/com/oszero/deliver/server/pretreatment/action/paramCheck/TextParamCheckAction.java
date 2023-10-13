package com.oszero.deliver.server.pretreatment.action.paramCheck;

import cn.dianhun.om.message.constant.CommonParamConstant;
import cn.dianhun.om.message.exception.PipelineProcessException;
import cn.dianhun.om.message.model.dto.SendTaskDto;
import cn.dianhun.om.message.pipeline.BusinessProcess;
import cn.dianhun.om.message.pipeline.ProcessContext;
import cn.dianhun.om.message.util.CommonUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author zbzbzzz
 * @email zbzbzzz@dianhun.cn
 * @date 2023/4/20 10:05
 */

@Service
public class TextParamCheckAction implements BusinessProcess<SendTaskDto> {
    @Override
    public void process(ProcessContext<SendTaskDto> context) {
        Map<String, String> paramMap = context.getProcessModel().getParamMap();
        if (CommonUtil.paramCheck(paramMap,CommonParamConstant.CONTENT)) {
            throw new PipelineProcessException("[text]参数不存在或者为空！");
        }
    }


}
