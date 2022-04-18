package com.zwy.zhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zwy.zhxy.mapper.StudentMapper;
import com.zwy.zhxy.pojo.LoginForm;
import com.zwy.zhxy.pojo.Student;
import com.zwy.zhxy.service.StudentService;
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
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student>
    implements StudentService{

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public Student login(LoginForm loginForm) {
        String name = loginForm.getUsername();
        String passwordStr = loginForm.getPassword();
        String password = MD5.encrypt(passwordStr);
        return studentMapper.selectByNameAndPassword(name, password);
    }

    @Override
    public IPage<Student> getClazzByOpr(Page<Student> page, Student student) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        String clazzName = student.getClazzName();
        String studentName = student.getName();
        queryWrapper.like(StringUtils.hasLength(clazzName), "clazz_name", clazzName)
                .like(StringUtils.hasLength(studentName), "name", studentName)
                .orderByDesc("id");
        return baseMapper.selectPage(page, queryWrapper);
    }
}




