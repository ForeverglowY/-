package com.zwy.zhxy.mapper;

import com.zwy.zhxy.pojo.Admin;
import com.zwy.zhxy.pojo.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Entity com.zwy.zhxy.pojo.Student
 */
@Repository
public interface StudentMapper extends BaseMapper<Student> {
    Student selectByNameAndPassword(@Param("name") String name, @Param("password") String password);
}




