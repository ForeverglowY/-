package com.zwy.zhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zwy.zhxy.pojo.Clazz;
import com.zwy.zhxy.pojo.Grade;
import com.zwy.zhxy.service.ClazzService;
import com.zwy.zhxy.mapper.ClazzMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 *
 */
@Service
@Transactional
public class ClazzServiceImpl extends ServiceImpl<ClazzMapper, Clazz>
    implements ClazzService{


    @Override
    public IPage<Clazz> getClazzByOpr(Page<Clazz> page, Clazz clazz) {
        QueryWrapper<Clazz> queryWrapper = new QueryWrapper<>();
        String gradeName = clazz.getGradeName();

        String clazzName = clazz.getName();
        queryWrapper.like(StringUtils.hasLength(gradeName), "grade_name", gradeName)
                .like(StringUtils.hasLength(clazzName), "name", clazz.getName())
                .orderByDesc("id");
        return baseMapper.selectPage(page, queryWrapper);
    }
}




