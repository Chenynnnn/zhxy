package com.cheny.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cheny.pojo.Grade;

import java.util.List;

public interface GradeService extends IService<Grade> {
    /**
     * 带条件的分页查询年级
     * @param page
     *      包含了页码pageNo和页条数pageSize的page对象
     * @param gradeName
     *      条件查询的条件
     * @return
     *      返回包含所有数据的IPage的对象
     */
    IPage<Grade> getByPageAndCondition(IPage<Grade> page, String gradeName);

    /**
     * 查询全部班级信息
     * @return
     *      返回班级对象集合
     */
    List<Grade> getAll();
}
