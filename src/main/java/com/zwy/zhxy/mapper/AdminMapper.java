package com.zwy.zhxy.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.zwy.zhxy.pojo.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @Entity com.zwy.zhxy.pojo.Admin
 */
@Repository
public interface AdminMapper extends BaseMapper<Admin> {

    Admin selectByNameAndPassword(@Param("name") String name, @Param("password") String password);
}




