package com.zwy.zhxy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zwy.zhxy.pojo.Clazz;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zwy.zhxy.pojo.Grade;

/**
 *
 */
public interface ClazzService extends IService<Clazz> {

    IPage<Clazz> getClazzByOpr(Page<Clazz> page, Clazz clazz);
}
