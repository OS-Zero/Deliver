package com.oszero.deliver.server.message.consumer.client;

/**
 * @author zbzbzzz
 * @email zbzbzzz@dianhun.cn
 * @date 2023/6/9 14:41
 */
public interface CallClient {

    String call(String subject, String content, String phone);

    Boolean state(String phone,String callId);
}
