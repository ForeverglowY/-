package com.zwy.zhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zwy.zhxy.pojo.Grade;
import com.zwy.zhxy.service.GradeService;
import com.zwy.zhxy.mapper.GradeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 *
 */
@Service
@Transactional
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade>
        implements GradeService {

    @Autowired
    private GradeMapper gradeMapper;

    @Override
    public IPage<Grade> getGradeByOpr(Page<Grade> page, String gradeName) {
        QueryWrapper<Grade> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.hasLength(gradeName), "name", gradeName)
                .orderByDesc("id");
        return baseMapper.selectPage(page, queryWrapper);
    }
}




