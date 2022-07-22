package com.vectorx.smartcampus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vectorx.smartcampus.mapper.GradeMapper;
import com.vectorx.smartcampus.pojo.Grade;
import com.vectorx.smartcampus.service.GradeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Transactional
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade> implements GradeService
{

    @Override
    public IPage<Grade> listGradeByOpr(Page<Grade> pageParam, String gradeName) {
        QueryWrapper<Grade> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(gradeName)) {
            queryWrapper.like("name", gradeName);
        }
        queryWrapper.orderByDesc("id");
        return baseMapper.selectPage(pageParam, queryWrapper);
    }

    @Override
    public List<Grade> listGrade() {
        return baseMapper.selectList(null);
    }
}
