package com.oszero.deliver.server.log;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 消息链路追踪日志类
 */
@Getter
@Component
public class MessageLinkTraceLogger {

    private final Logger logger;

    public MessageLinkTraceLogger(@Value("${message-link-trace.file.path}") String logFilePath, @Value("${message-link-trace.file.name}") String logFileName) {
        String fullPath = logFilePath + "/" + logFileName;
        // 使用 LoggerFactory 创建 Logger
        this.logger = LoggerFactory.getLogger("MessageLinkTraceLogger");
    }

}
