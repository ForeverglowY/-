package com.zwy.zhxy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zwy.zhxy.pojo.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zwy.zhxy.pojo.LoginForm;

/**
 *
 */
public interface AdminService extends IService<Admin> {
    Admin login(LoginForm loginForm);

    IPage<Admin> getClazzByOpr(Page<Admin> page, String adminName);
}
