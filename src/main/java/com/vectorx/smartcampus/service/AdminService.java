package com.vectorx.smartcampus.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.vectorx.smartcampus.pojo.Admin;
import com.vectorx.smartcampus.pojo.LoginForm;

public interface AdminService extends IService<Admin>
{
    Admin login(LoginForm loginForm);

    Admin getAdminById(Long userId);

    IPage<Admin> listAdminByOpr(Page<Admin> pageParam, String adminName);

}
