package com.oszero.deliver.server.cache.common;

import cn.hutool.crypto.digest.MD5;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 钉钉、企微、飞书 token 缓存 key 生成器
 *
 * @author oszero
 * @version 1.0.0
 */
@Component
public class TokenKeyGenerator implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
        return MD5.create().digestHex(params[0].toString());
    }
}
