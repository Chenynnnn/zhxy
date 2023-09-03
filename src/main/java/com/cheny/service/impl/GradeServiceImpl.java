package com.cheny.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cheny.mapper.GradeMapper;
import com.cheny.pojo.Grade;
import com.cheny.service.GradeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("gradeService")
@Transactional
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade> implements GradeService {
    @Autowired
    private GradeMapper gradeMapper;

    @Override
    public IPage<Grade> getByPageAndCondition(IPage<Grade> page, String gradeName) {
        LambdaQueryWrapper<Grade> lqw = new LambdaQueryWrapper<>();
        //如果gradeName不为空，则进行条件查询
        lqw.like(StringUtils.isNotBlank(gradeName), Grade::getName, gradeName);
        //根据id升序排序
        lqw.orderByAsc(Grade::getId);
        gradeMapper.selectPage(page, lqw);

        return page;
    }

    @Override
    public List<Grade> getAll() {
        List<Grade> grades = gradeMapper.selectList(null);
        return grades;
    }

}
