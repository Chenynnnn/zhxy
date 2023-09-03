package com.cheny.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cheny.mapper.StudentMapper;
import com.cheny.pojo.Student;
import com.cheny.service.StudentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("studentService")
@Transactional
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
    @Autowired
    private StudentMapper studentMapper;

    @Override
    public Student selectAdminByNameAndPassword(String username, String encrypt) {
        LambdaQueryWrapper<Student> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Student::getName, username).eq(Student::getPassword, encrypt);
        Student student = studentMapper.selectOne(lqw);
        return student;
    }

    @Override
    public IPage<Student> getByPageAndCondition(IPage<Student> page, Student student) {
        LambdaQueryWrapper<Student> lqw = new LambdaQueryWrapper<>();
        lqw.eq(StringUtils.isNotBlank(student.getClazzName()), Student::getClazzName, student.getClazzName())
                .like(StringUtils.isNotBlank(student.getName()), Student::getName, student.getName());

        studentMapper.selectPage(page, lqw);

        return page;
    }


}
