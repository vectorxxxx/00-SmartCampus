package com.vectorx.smartcampus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vectorx.smartcampus.mapper.StudentMapper;
import com.vectorx.smartcampus.pojo.LoginForm;
import com.vectorx.smartcampus.pojo.Student;
import com.vectorx.smartcampus.service.StudentService;
import com.vectorx.smartcampus.util.MD5;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 *
 */
@Service("stuService")
@Transactional
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService
{

    @Override
    public Student login(LoginForm loginForm) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", loginForm.getUsername());
        queryWrapper.eq("password", MD5.encrypt(loginForm.getPassword()));
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public Student getStudentById(Long userId) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", userId);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public IPage<Student> listStudentByOpr(Page<Student> pageParam, Student student) {
        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(student.getName())) {
            studentQueryWrapper.like("name", student.getName());
        }
        if (!StringUtils.isEmpty(student.getClazzName())) {
            studentQueryWrapper.like("clazz_name", student.getClazzName());
        }
        studentQueryWrapper.orderByDesc("id");
        Page<Student> studentPage = baseMapper.selectPage(pageParam, studentQueryWrapper);
        return studentPage;
    }

}
