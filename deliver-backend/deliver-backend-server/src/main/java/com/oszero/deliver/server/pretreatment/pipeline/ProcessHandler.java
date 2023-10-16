package com.oszero.deliver.server.pretreatment.pipeline;

import cn.hutool.core.collection.CollUtil;
import com.oszero.deliver.server.exception.PipelineProcessException;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 流程控制器
 *
 */
@Slf4j
@Data
@Accessors(chain = true)
@SuppressWarnings("all")
public class ProcessHandler {

    /**
     * 模板映射
     */
    private Map<String, ProcessTemplate> templateConfig = null;

    /**
     * 执行责任链
     *
     * @param context
     * @return 返回上下文内容
     */
    public ProcessContext process(ProcessContext context) {

        //1. 前置检查
        preCheck(context);

        //2. 遍历流程节点
        List<BusinessProcess> processList = templateConfig.get(context.getCode()).getProcessList();
        for (BusinessProcess businessProcess : processList) {
            businessProcess.process(context);
        }
        return context;
    }

    /**
     * 执行前检查检查参数是否达到要去，出错则抛出异常
     *
     * @param context 执行上下文
     */
    private void preCheck(ProcessContext context) {
        if (Objects.isNull(context)) {
            throw new PipelineProcessException("执行上下文为空！");
        }

        String businessCode = context.getCode();

        ProcessTemplate processTemplate = templateConfig.get(businessCode);
        if (Objects.isNull(processTemplate)) {
            throw new PipelineProcessException("无法找到执行模板！");
        }

        List<BusinessProcess> processList = processTemplate.getProcessList();
        if (CollUtil.isEmpty(processList)) {
            throw new PipelineProcessException("执行链路为空！");
        }

    }
}
