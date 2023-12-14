package com.oszero.deliver.admin.util;

import cn.hutool.crypto.symmetric.AES;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * AES 对称加密工具类
 *
 * @author oszero
 * @version 1.0.0
 */
@Component
public class AdminAesUtils {

    /**
     * 秘钥
     */
    @Value("${aes.key}")
    private String aesKey;

    /**
     * 加密
     *
     * @param content 文本
     * @return 密文
     */
    public String encrypt(String content) {
        byte[] key = adjustKeyLength(aesKey.getBytes(StandardCharsets.UTF_8), 24);
        AES aes = new AES(key);
        //构建
        return aes.encryptHex(content);
    }

    /**
     * 解密
     *
     * @param encrypt 密文
     * @return 文本
     */
    public String decrypt(String encrypt) {
        byte[] key = adjustKeyLength(aesKey.getBytes(StandardCharsets.UTF_8), 24);
        AES aes = new AES(key);
        //构建
        return aes.decryptStr(encrypt);
    }

    /**
     * 加密秘钥长度调整
     *
     * @param key           秘钥
     * @param desiredLength 长度 128、192、256
     * @return 调整后秘钥字节数组
     */
    public byte[] adjustKeyLength(byte[] key, int desiredLength) {
        if (key.length == desiredLength) {
            return key; // 密钥长度已符合要求
        }
        // 密钥长度不符，调整密钥长度
        return Arrays.copyOf(key, desiredLength);
    }
}
