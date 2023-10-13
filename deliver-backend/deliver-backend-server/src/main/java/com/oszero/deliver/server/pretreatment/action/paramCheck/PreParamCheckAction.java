package com.oszero.deliver.server.pretreatment.action.paramCheck;

import cn.dianhun.om.message.exception.PipelineProcessException;
import cn.dianhun.om.message.model.dto.SendTaskDto;
import cn.dianhun.om.message.pipeline.BusinessProcess;
import cn.dianhun.om.message.pipeline.ProcessContext;
import cn.hutool.core.collection.CollUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author zbzbzzz
 * @email zbzbzzz@dianhun.cn
 * @date 2023/4/20 10:39
 */

@Service
@Slf4j
public class PreParamCheckAction implements BusinessProcess<SendTaskDto> {
    @Override
    public void process(ProcessContext<SendTaskDto> context) {
        SendTaskDto sendTaskDto = context.getProcessModel();
        if (Objects.isNull(context.getProcessModel().getStrategyId())) {
            throw new PipelineProcessException("策略id不能为空！");
        }
        if (CollUtil.isEmpty(context.getProcessModel().getReceiver())) {
            throw new PipelineProcessException("没有消息接收者！");
        }
        Set<String> set = new HashSet<>(context.getProcessModel().getReceiver());
        if (set.size() != context.getProcessModel().getReceiver().size()) {
            throw new PipelineProcessException("含有重复的消息接收者！");
        }
        if (Objects.isNull(sendTaskDto.getRetry())) {
            sendTaskDto.setRetry(0);
        }
        // TODO 可扩展重试次数功能
    }
}
