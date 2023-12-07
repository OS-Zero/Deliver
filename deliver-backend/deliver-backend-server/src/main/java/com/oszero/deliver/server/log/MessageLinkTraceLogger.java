package com.oszero.deliver.server.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 消息链路追踪日志类
 *
 * @author oszero
 * @version 1.0.0
 */
public class MessageLinkTraceLogger {

    private final static Logger logger = LoggerFactory.getLogger("MessageLinkTraceLogger");

    public static void info(String s) {
        logger.info(s);
    }

    public static void info(String s, Object... objects) {
        logger.info(s, objects);
    }

    public static void error(String s) {
        logger.error(s);
    }

    public static void error(String s, Object... objects) {
        logger.error(s, objects);
    }
}
