package com.cheny.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cheny.pojo.Student;

import java.util.List;

public interface StudentService extends IService<Student> {
    /**
     * 根据用户名和密码查询学生账号
     * @param username
     *      用户名
     * @param encrypt
     *      加密之后的密码
     * @return
     *      返回值为null表示账号不存在，返回值不为null表示账号存在
     */
    Student selectAdminByNameAndPassword(String username, String encrypt);

    /**
     * 分页条件查询
     * @param page
     *      分页的参数
     * @param student
     *      条件
     * @return
     *      返回分页结果集
     */
    IPage<Student> getByPageAndCondition(IPage<Student> page, Student student);
}
