package com.cheny.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cheny.mapper.TeacherMapper;
import com.cheny.pojo.Admin;
import com.cheny.pojo.Teacher;
import com.cheny.service.TeacherService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("teacherService")
@Transactional
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService{
    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public Teacher selectAdminByNameAndPassword(String username, String encrypt) {
        LambdaQueryWrapper<Teacher> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Teacher::getName, username).eq(Teacher::getPassword, encrypt);
        Teacher teacher = teacherMapper.selectOne(lqw);
        return teacher;
    }

    @Override
    public IPage<Teacher> getByPageAndCondition(IPage<Teacher> page, Teacher teacher) {
        LambdaQueryWrapper<Teacher> lqw = new LambdaQueryWrapper<>();
        lqw.eq(StringUtils.isNotBlank(teacher.getClazzName()), Teacher::getClazzName, teacher.getClazzName())
                .like(StringUtils.isNotBlank(teacher.getName()), Teacher::getName, teacher.getName());
        teacherMapper.selectPage(page, lqw);
        return page;
    }
}
