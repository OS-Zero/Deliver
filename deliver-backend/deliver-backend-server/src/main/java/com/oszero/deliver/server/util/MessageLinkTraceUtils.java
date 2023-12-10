package com.oszero.deliver.server.util;

import com.oszero.deliver.server.log.MessageLinkTraceLogger;
import com.oszero.deliver.server.model.dto.SendTaskDto;

/**
 * MessageLinkTrace 工具类
 *
 * @author oszero
 * @version 1.0.0
 */
public class MessageLinkTraceUtils {
    public static void recordMessageLifecycleInfoLog(SendTaskDto sendTaskDto, String msg) {
        MessageLinkTraceLogger.info("消息状态: 正常, 消息链路 ID: {}, 模板 ID: {}, 应用 ID: {}, 接收人列表: {}, 是否重试消息: {}, 重试次数剩余: {}, 请求 IP: {}, 处理信息: {}"
                , sendTaskDto.getTraceId()
                , sendTaskDto.getTemplateId()
                , sendTaskDto.getAppId()
                , sendTaskDto.getUsers()
                , sendTaskDto.getRetried() == 1
                , sendTaskDto.getRetry()
                , sendTaskDto.getClientIp()
                , msg);
    }

    public static void recordMessageLifecycleInfoLog(String msg) {
        MessageLinkTraceLogger.info(msg);
    }

    public static void recordMessageLifecycleErrorLog(SendTaskDto sendTaskDto, String msg) {
        MessageLinkTraceLogger.error("消息状态: 异常, 消息链路 ID: {}, 模板 ID: {}, 应用 ID: {}, 接收人列表: {}, 是否重试消息: {}, 重试次数剩余: {}, 请求 IP: {}, 处理信息: {}"
                , sendTaskDto.getTraceId()
                , sendTaskDto.getTemplateId()
                , sendTaskDto.getAppId()
                , sendTaskDto.getUsers()
                , sendTaskDto.getRetried() == 1
                , sendTaskDto.getRetry()
                , sendTaskDto.getClientIp()
                , msg);
    }

    public static void recordMessageLifecycleErrorLog(String msg) {
        MessageLinkTraceLogger.error(msg);
    }

    public static void recordMessageLifecycleError2InfoLog(SendTaskDto sendTaskDto, String msg) {
        recordMessageLifecycleInfoLog(formatMessageLifecycleErrorLogMsg(sendTaskDto, msg));
    }

    public static void recordMessageLifecycleError2InfoLog(String msg) {
        MessageLinkTraceLogger.info(msg);
    }

    public static String formatMessageLifecycleErrorLogMsg(SendTaskDto sendTaskDto, String msg) {
        return String.format("消息状态: 异常, 消息链路 ID: %s, 模板 ID: %s, 应用 ID: %s, 接收人列表: %s, 是否重试消息: %s, 重试次数剩余: %s, 请求 IP: %s, 处理信息: %s"
                , sendTaskDto.getTraceId()
                , sendTaskDto.getTemplateId()
                , sendTaskDto.getAppId()
                , sendTaskDto.getUsers()
                , sendTaskDto.getRetried() == 1
                , sendTaskDto.getRetry()
                , sendTaskDto.getClientIp()
                , msg);
    }

    public static void recordErrorLogAndError2InfoLog(SendTaskDto sendTaskDto, String msg) {
        String errorLogMsg = formatMessageLifecycleErrorLogMsg(sendTaskDto, msg);
        recordMessageLifecycleError2InfoLog(errorLogMsg);
        recordMessageLifecycleErrorLog(errorLogMsg);
    }
}
