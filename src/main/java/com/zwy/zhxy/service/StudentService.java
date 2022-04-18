package com.zwy.zhxy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zwy.zhxy.pojo.LoginForm;
import com.zwy.zhxy.pojo.Student;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface StudentService extends IService<Student> {

    Student login(LoginForm loginForm);

    IPage<Student> getClazzByOpr(Page<Student> page, Student student);
}
