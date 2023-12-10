package com.oszero.deliver.server.mq.consumer.common;

import cn.hutool.core.util.StrUtil;
import com.oszero.deliver.server.constant.TraceIdConstant;
import com.oszero.deliver.server.enums.StatusEnum;
import com.oszero.deliver.server.exception.MessageException;
import com.oszero.deliver.server.handler.BaseHandler;
import com.oszero.deliver.server.mq.producer.Producer;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.util.MDCUtils;
import com.oszero.deliver.server.util.MessageLinkTraceUtils;
import com.oszero.deliver.server.web.service.MessageRecordService;

import java.util.Objects;

/**
 * 消息队列通用处理
 *
 * @author oszero
 * @version 1.0.0
 */
public class MQCommonConsumer {

    public static void tryHandle(SendTaskDto sendTaskDto, BaseHandler handler) throws Exception {

        // 记录链路追踪 id
        String traceId = sendTaskDto.getTraceId();
        if (StrUtil.isBlank(traceId)) {
            throw new MessageException(sendTaskDto, "traceId 为空");
        }
        MDCUtils.put(TraceIdConstant.TRACE_ID, traceId);

        MessageLinkTraceUtils.recordMessageLifecycleInfoLog(sendTaskDto, "接收到消息队列消息，消息已送达消费者");

        // 处理器处理
        handler.doHandle(sendTaskDto);
    }

    public static void catchHandle(SendTaskDto sendTaskDto, Exception exception, MessageRecordService messageRecordService, Producer producer) {
        if (!Objects.isNull(sendTaskDto)) {
            // 记录错误日志
            MessageLinkTraceUtils.recordMessageLifecycleErrorLog(exception.getMessage());
            // 记录异常信息到生命周期日志中
            MessageLinkTraceUtils.recordMessageLifecycleError2InfoLog(exception.getMessage());
            // 记录消息消费失败
            sendTaskDto.getUsers().forEach(user -> messageRecordService.saveMessageRecord(sendTaskDto, StatusEnum.OFF, user));

            if (sendTaskDto.getRetry() > 0) {
                // 重新发送
                sendTaskDto.setRetry(sendTaskDto.getRetry() - 1);
                sendTaskDto.setRetried(StatusEnum.ON.getStatus());
                producer.sendMessage(sendTaskDto);

                MessageLinkTraceUtils.recordMessageLifecycleInfoLog(sendTaskDto, "重试消息已发送");
            } else {
                // 记录错误日志
                MessageLinkTraceUtils.recordMessageLifecycleErrorLog(sendTaskDto, "消息发送失败，重试次数已用完！！！");
                // 记录异常信息到生命周期日志中
                MessageLinkTraceUtils.recordMessageLifecycleError2InfoLog(sendTaskDto, "消息发送失败，重试次数已用完！！！");
            }
        } else {
            // 记录错误日志
            MessageLinkTraceUtils.recordMessageLifecycleErrorLog("消息消费失败，" + exception.getMessage() + "！！！");
            // 记录异常信息到生命周期日志中
            MessageLinkTraceUtils.recordMessageLifecycleError2InfoLog("消息消费失败，" + exception.getMessage() + "！！！");
        }
        MDCUtils.clear();
    }
}
