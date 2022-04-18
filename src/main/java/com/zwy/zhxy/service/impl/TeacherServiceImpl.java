package com.zwy.zhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zwy.zhxy.pojo.Grade;
import com.zwy.zhxy.pojo.LoginForm;
import com.zwy.zhxy.pojo.Teacher;
import com.zwy.zhxy.service.TeacherService;
import com.zwy.zhxy.mapper.TeacherMapper;
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
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher>
    implements TeacherService{

    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public Teacher login(LoginForm loginForm) {
        String name = loginForm.getUsername();
        String passwordStr = loginForm.getPassword();
        String password = MD5.encrypt(passwordStr);
        return teacherMapper.selectByNameAndPassword(name, password);
    }

    @Override
    public IPage<Teacher> getClazzByOpr(Page<Teacher> page, Teacher teacher) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        String clazzName = teacher.getClazzName();
        String teacherName = teacher.getName();
        queryWrapper.like(StringUtils.hasLength(clazzName), "clazz_name", clazzName)
                .like(StringUtils.hasLength(teacherName), "name", teacherName)
                .orderByDesc("id");
        return baseMapper.selectPage(page, queryWrapper);
    }
}




