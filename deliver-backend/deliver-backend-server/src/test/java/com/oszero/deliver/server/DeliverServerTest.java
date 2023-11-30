package com.oszero.deliver.server;

import com.oszero.deliver.server.util.AesUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DeliverServerTest {

    @Autowired
    private AesUtils aesUtils;

    /**
     * 测试 AES 加密功能
     */
    @Test
    public void testAes() {
        System.out.println(aesUtils.encrypt(""));
    }
}
