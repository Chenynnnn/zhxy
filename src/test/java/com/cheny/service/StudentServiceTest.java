package com.cheny.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StudentServiceTest {
    @Autowired
    private StudentService studentService;

    @Test
    void testSelectAdminByNameAndPassword() {
        String username = "张小明";
        String password = "e10adc3949ba59abbe56e057f20f883e";
        studentService.selectAdminByNameAndPassword(username, password);
    }

}
