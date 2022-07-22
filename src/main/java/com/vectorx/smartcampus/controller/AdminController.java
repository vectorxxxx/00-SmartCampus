package com.vectorx.smartcampus.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vectorx.smartcampus.pojo.Admin;
import com.vectorx.smartcampus.service.AdminService;
import com.vectorx.smartcampus.util.MD5;
import com.vectorx.smartcampus.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sms/adminController")
public class AdminController
{
    @Autowired
    private AdminService adminService;

    @GetMapping("/getAllAdmin/{pageNo}/{pageSize}")
    public Result getAllAdmin(@PathVariable("pageNo") Integer pageNo, @PathVariable("pageSize") Integer pageSize,
            String adminName) {
        Page<Admin> page = new Page<>(pageNo, pageSize);
        IPage<Admin> iPage = adminService.listAdminByOpr(page, adminName);
        return Result.ok(iPage);
    }

    @PostMapping("/saveOrUpdateAdmin")
    public Result saveOrUpdateAdmin(@RequestBody Admin admin) {
        if (admin.getId() == null || admin.getId() == 0) {
            admin.setPassword(MD5.encrypt(admin.getPassword()));
        }
        adminService.saveOrUpdate(admin);
        return Result.ok();
    }

    @DeleteMapping("/deleteAdmin")
    public Result deleteAdmin(@RequestBody List<Integer> ids) {
        adminService.removeByIds(ids);
        return Result.ok();
    }

}
