package com.vectorx.smartcampus.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.vectorx.smartcampus.pojo.LoginForm;
import com.vectorx.smartcampus.pojo.Student;

public interface StudentService extends IService<Student>
{

    Student login(LoginForm loginForm);

    Student getStudentById(Long userId);

    IPage<Student> listStudentByOpr(Page<Student> pageParam, Student student);
}
