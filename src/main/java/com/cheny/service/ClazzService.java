package com.cheny.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cheny.pojo.Clazz;
import com.cheny.pojo.Student;

import java.util.List;

public interface ClazzService extends IService<Clazz> {
    /**
     * 分页带条件查询班级信息
     * @param page
     *      分页参数
     * @param clazz
     *      条件
     * @return
     *      返回分页结果集
     */
    IPage<Clazz> getByPageAndCondition(IPage<Clazz> page, Clazz clazz);

    /**
     * 获取
     * @return
     */
    List<Clazz> getAll();
}
