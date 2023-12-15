package com.oszero.deliver.server.client.call.factory;

import com.oszero.deliver.server.client.call.CallClient;
import com.oszero.deliver.server.client.call.impl.AliYunCallClient;
import com.oszero.deliver.server.client.call.impl.TencentCallClient;
import com.oszero.deliver.server.enums.CallProviderTypeEnum;
import com.oszero.deliver.server.exception.MessageException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 电话客户端工厂类
 *
 * @author oszero
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class CallFactory {
    private final AliYunCallClient aliYunCallClient;
    private final TencentCallClient tencentCallClient;

    public CallClient getClient(String callProvider) {
        if (CallProviderTypeEnum.ALI_YUN.getName().equals(callProvider)) {
            return aliYunCallClient;
        } else if (CallProviderTypeEnum.TENCENT.getName().equals(callProvider)) {
            return tencentCallClient;
        } else {
            throw new MessageException("暂时不提供指定短信服务提供商：" + callProvider);
        }
    }
}
