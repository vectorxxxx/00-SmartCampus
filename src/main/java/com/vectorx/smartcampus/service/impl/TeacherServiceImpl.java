package com.vectorx.smartcampus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vectorx.smartcampus.mapper.TeacherMapper;
import com.vectorx.smartcampus.pojo.LoginForm;
import com.vectorx.smartcampus.pojo.Teacher;
import com.vectorx.smartcampus.service.TeacherService;
import com.vectorx.smartcampus.util.MD5;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service("teaService")
@Transactional
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService
{

    @Override
    public Teacher login(LoginForm loginForm) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", loginForm.getUsername());
        queryWrapper.eq("password", MD5.encrypt(loginForm.getPassword()));
        Teacher teacher = baseMapper.selectOne(queryWrapper);
        return teacher;
    }

    @Override
    public Teacher getTeacherById(Long userId) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", userId);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public IPage<Teacher> listTeacherByOpr(Page<Teacher> paraParam, Teacher teacher) {
        QueryWrapper queryWrapper = new QueryWrapper();
        if (!StringUtils.isEmpty(teacher.getName())) {
            queryWrapper.like("name", teacher.getName());
        }
        if (!StringUtils.isEmpty(teacher.getClazzName())) {
            queryWrapper.eq("clazz_name", teacher.getClazzName());
        }
        queryWrapper.orderByDesc("id");

        Page<Teacher> page = baseMapper.selectPage(paraParam, queryWrapper);
        return page;
    }

}
