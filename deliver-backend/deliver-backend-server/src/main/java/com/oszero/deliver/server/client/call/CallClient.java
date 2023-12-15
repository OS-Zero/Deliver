package com.oszero.deliver.server.client.call;

import com.oszero.deliver.server.model.app.call.CallApp;
import com.oszero.deliver.server.model.dto.common.SendTaskDto;

import java.util.concurrent.ExecutionException;

/**
 * 电话客户端接口
 *
 * @author oszero
 * @version 1.0.0
 */
public interface CallClient {

    void sendCall(CallApp callApp, SendTaskDto sendTaskDto) throws ExecutionException, InterruptedException;

}
