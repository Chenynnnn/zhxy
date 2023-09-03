package com.cheny.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cheny.pojo.Teacher;

public interface TeacherService extends IService<Teacher> {
    /**
     * 根据用户名和密码查询老师账号
     * @param username
     *      用户名
     * @param encrypt
     *      加密之后的密码
     * @return
     *      返回值为null表示账号不存在，返回值不为null表示账号存在
     */
    Teacher selectAdminByNameAndPassword(String username, String encrypt);

    /**
     * 分页带条件查询
     * @param page
     *      分页参数
     * @param teacher
     *      条件
     * @return
     *      返回分页结果
     */
    IPage<Teacher> getByPageAndCondition(IPage<Teacher> page, Teacher teacher);
}
