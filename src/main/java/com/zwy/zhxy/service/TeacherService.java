package com.zwy.zhxy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zwy.zhxy.pojo.Admin;
import com.zwy.zhxy.pojo.LoginForm;
import com.zwy.zhxy.pojo.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface TeacherService extends IService<Teacher> {
    Teacher login(LoginForm loginForm);

    IPage<Teacher> getClazzByOpr(Page<Teacher> page, Teacher teacher);
}
