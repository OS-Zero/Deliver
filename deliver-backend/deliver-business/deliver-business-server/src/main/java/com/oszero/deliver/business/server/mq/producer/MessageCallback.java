package com.oszero.deliver.business.server.mq.producer;

/**
 * @author oszero
 * @version 1.0.2
 */
public interface MessageCallback {
    /**
     * 发送消息回调
     */
    void messageCallback(String messageId, Object message, boolean success);
}
