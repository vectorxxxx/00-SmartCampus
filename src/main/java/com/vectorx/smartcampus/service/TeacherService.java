package com.vectorx.smartcampus.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.vectorx.smartcampus.pojo.LoginForm;
import com.vectorx.smartcampus.pojo.Teacher;

public interface TeacherService extends IService<Teacher>
{

    Teacher login(LoginForm loginForm);

    Teacher getTeacherById(Long userId);

    IPage<Teacher> listTeacherByOpr(Page<Teacher> paraParam, Teacher teacher);
}
