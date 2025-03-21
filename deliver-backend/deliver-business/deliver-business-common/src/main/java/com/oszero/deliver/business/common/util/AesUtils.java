/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.oszero.deliver.business.common.util;

import cn.hutool.crypto.symmetric.AES;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * @author oszero
 * @version 1.0.0
 */
@Component
public class AesUtils {

    @Value("${aes.key}")
    private String aesKey;

    public static AesUtils newInstance(String aesKey) {
        if (aesKey == null || aesKey.isEmpty()) {
            throw new IllegalArgumentException("aesKey is null or empty");
        }
        AesUtils aesUtils = new AesUtils();
        aesUtils.aesKey = aesKey;
        return aesUtils;
    }

    public String encrypt(String content) {
        byte[] key = adjustKeyLength(aesKey.getBytes(StandardCharsets.UTF_8), 24);
        AES aes = new AES(key);
        return aes.encryptHex(content);
    }

    public String decrypt(String encrypt) {
        byte[] key = adjustKeyLength(aesKey.getBytes(StandardCharsets.UTF_8), 24);
        AES aes = new AES(key);
        return aes.decryptStr(encrypt);
    }

    public byte[] adjustKeyLength(byte[] key, int desiredLength) {
        if (key.length == desiredLength) {
            return key;
        }
        return Arrays.copyOf(key, desiredLength);
    }
}
