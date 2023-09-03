package com.cheny.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cheny.mapper.AdminMapper;
import com.cheny.pojo.Admin;
import com.cheny.service.AdminService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("adminService")
@Transactional
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin selectAdminByNameAndPassword(String username, String encrypt) {
        LambdaQueryWrapper<Admin> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Admin::getName, username).eq(Admin::getPassword, encrypt);
        return adminMapper.selectOne(lqw);
    }

    @Override
    public IPage<Admin> getByPageAndCondition(IPage<Admin> page, String adminName) {
        LambdaQueryWrapper<Admin> lqw = new LambdaQueryWrapper<>();
        lqw.like(StringUtils.isNotBlank(adminName), Admin::getName, adminName);
        adminMapper.selectPage(page, lqw);
        return page;
    }
}
