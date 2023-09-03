package com.cheny.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cheny.mapper.ClazzMapper;
import com.cheny.pojo.Clazz;
import com.cheny.service.ClazzService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("clazzService")
@Transactional
public class ClazzServiceImpl extends ServiceImpl<ClazzMapper, Clazz> implements ClazzService {
    @Autowired
    private ClazzMapper clazzMapper;

    @Override
    public IPage<Clazz> getByPageAndCondition(IPage<Clazz> page, Clazz clazz) {
        LambdaQueryWrapper<Clazz> lqw = new LambdaQueryWrapper<>();
        lqw.eq(StringUtils.isNotBlank(clazz.getGradeName()), Clazz::getGradeName, clazz.getGradeName())
                .like(StringUtils.isNotBlank(clazz.getName()), Clazz::getName, clazz.getName());

        clazzMapper.selectPage(page, lqw);

        return page;
    }

    @Override
    public List<Clazz> getAll() {
        return clazzMapper.selectList(null);
    }
}
