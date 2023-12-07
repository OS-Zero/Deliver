package com.oszero.deliver.server.pretreatment.link;

import cn.hutool.core.collection.CollUtil;
import com.oszero.deliver.server.constant.TraceIdConstant;
import com.oszero.deliver.server.exception.MessageException;
import com.oszero.deliver.server.log.MessageLinkTraceLogger;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.util.IpUtils;
import com.oszero.deliver.server.util.MDCUtils;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 责任链处理器
 *
 * @author oszero
 * @version 1.0.0
 */
@Slf4j
@Data
@Accessors(chain = true)
@SuppressWarnings("all")
public class LinkHandler {

    /**
     * 模板映射
     */
    private Map<String, LinkTemplate> templateConfig = null;
    private MessageLinkTraceLogger messageLinkTraceLogger = null;


    /**
     * 执行责任链
     *
     * @param context
     * @return 返回上下文内容
     */
    public LinkContext process(LinkContext context) {

        //1. 前置检查
        preCheck(context);
        SendTaskDto sendTaskDto = (SendTaskDto) context.getProcessModel();
        messageLinkTraceLogger.info("消息链路 ID: {}, 模板 ID: {}, 应用 ID: {}, 接收人列表: {}, 是否重试消息: {}, 重试次数剩余: {}, 请求 IP: {}, 处理信息: {}"
                , MDCUtils.get(TraceIdConstant.TRACE_ID)
                ,sendTaskDto.getTemplateId()
                ,sendTaskDto.getAppId()
                ,sendTaskDto.getUsers()
                ,sendTaskDto.getRetried()
                ,sendTaskDto.getRetry()
                ,IpUtils.getClientIp()
                ,"前置检查成功");

        //2. 遍历流程节点
        List<BusinessLink> processList = templateConfig.get(context.getCode()).getProcessList();
        for (BusinessLink businessLink : processList) {
            businessLink.process(context);
        }
        return context;
    }

    /**
     * 执行前检查检查参数是否达到要求，出错则抛出异常
     *
     * @param context 执行上下文
     */
    private void preCheck(LinkContext context) {
        if (Objects.isNull(context)) {
            throw new MessageException("执行上下文为空！");
        }

        String businessCode = context.getCode();

        LinkTemplate linkTemplate = templateConfig.get(businessCode);
        if (Objects.isNull(linkTemplate)) {
            throw new MessageException("无法找到执行模板！");
        }

        List<BusinessLink> processList = linkTemplate.getProcessList();
        if (CollUtil.isEmpty(processList)) {
            throw new MessageException("执行链路为空！");
        }

    }
}
