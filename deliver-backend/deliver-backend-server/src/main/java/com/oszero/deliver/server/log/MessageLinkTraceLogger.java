package com.oszero.deliver.server.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 消息链路追踪日志类
 *
 * @author oszero
 * @version 1.0.0
 */
@Component
public class MessageLinkTraceLogger {

    private final Logger logger;

    public MessageLinkTraceLogger() {
        // 使用 LoggerFactory 创建 Logger
        this.logger = LoggerFactory.getLogger("MessageLinkTraceLogger");
    }

    public void info(String s) {
        this.logger.info(s);
    }


    public void info(String s, Object... objects) {
        this.logger.info(s, objects);
    }

    public void error(String s) {
        this.logger.error(s);
    }

    public void error(String s, Object... objects) {
        this.logger.error(s, objects);
    }
}
