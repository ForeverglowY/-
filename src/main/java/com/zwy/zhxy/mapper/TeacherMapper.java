package com.zwy.zhxy.mapper;

import com.zwy.zhxy.pojo.Admin;
import com.zwy.zhxy.pojo.Teacher;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Entity com.zwy.zhxy.pojo.Teacher
 */
@Repository
public interface TeacherMapper extends BaseMapper<Teacher> {
    Teacher selectByNameAndPassword(@Param("name") String name, @Param("password") String password);
}




