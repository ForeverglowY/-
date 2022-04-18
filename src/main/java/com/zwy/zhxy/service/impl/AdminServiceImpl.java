package com.zwy.zhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zwy.zhxy.mapper.AdminMapper;
import com.zwy.zhxy.pojo.Admin;
import com.zwy.zhxy.pojo.LoginForm;
import com.zwy.zhxy.service.AdminService;
import com.zwy.zhxy.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 *
 */
@Service
@Transactional
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin>
        implements AdminService {

    @Autowired
    AdminMapper adminMapper;

    @Override
    public Admin login(LoginForm loginForm) {
        String name = loginForm.getUsername();
        String passwordStr = loginForm.getPassword();
        String password = MD5.encrypt(passwordStr);
        return adminMapper.selectByNameAndPassword(name, password);
    }

    @Override
    public IPage<Admin> getClazzByOpr(Page<Admin> page, String adminName) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.hasLength(adminName), "name", adminName)
                .orderByDesc("id");
        return baseMapper.selectPage(page, queryWrapper);
    }
}




