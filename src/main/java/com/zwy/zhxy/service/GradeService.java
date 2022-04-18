package com.zwy.zhxy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zwy.zhxy.pojo.Grade;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface GradeService extends IService<Grade> {

    IPage<Grade> getGradeByOpr(Page<Grade> page, String gradeName);
}
