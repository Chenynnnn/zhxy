package com.cheny;

import com.cheny.util.MD5;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MyZhxyApplicationTests {

    @Test
    void testMD5() {
        String code1 = "123456";
        String code2 = "123456";
        String encrypt1 = MD5.encrypt(code1);
        String encrypt2 = MD5.encrypt(code2);
        System.out.println(encrypt1);
        System.out.println(encrypt2);

    }

}
