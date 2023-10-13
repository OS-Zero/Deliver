package com.oszero.deliver.server.message.consumer.client.impl;

import com.oszero.deliver.server.message.consumer.client.CallClient;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AliCallClient implements CallClient {

    @Override
    public String call(String subject, String content, String phone) {
        return null;
    }

    @Override
    public Boolean state(String phone, String callId) {
        return null;
    }
}
