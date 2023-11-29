package com.oszero.deliver.server;

import com.oszero.deliver.server.util.AesUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DeliverServerTest {

    @Autowired
    private AesUtils aesUtils;

    @Test
    public void testAes() {
        System.out.println(aesUtils.encrypt("oszero"));
        System.out.println(aesUtils.decrypt(aesUtils.encrypt("oszero")));
    }
}
