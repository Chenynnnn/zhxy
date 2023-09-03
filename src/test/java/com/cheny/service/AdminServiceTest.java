package com.cheny.service;

import com.cheny.pojo.Admin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AdminServiceTest {
    @Autowired
    private AdminService adminService;

    @Test
    void testSelectAdminByNameAndPassword(){
        String username = "admin";
        String password = "21232f297a57a5a743894a0e4a801fc3";
        Admin admin = adminService.selectAdminByNameAndPassword(username, password);
        System.out.println(admin);
    }
}
