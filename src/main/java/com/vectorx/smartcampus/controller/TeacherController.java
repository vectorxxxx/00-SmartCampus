package com.vectorx.smartcampus.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vectorx.smartcampus.pojo.Teacher;
import com.vectorx.smartcampus.service.TeacherService;
import com.vectorx.smartcampus.util.MD5;
import com.vectorx.smartcampus.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sms/teacherController")
public class TeacherController
{

    @Autowired
    private TeacherService teacherService;

    @GetMapping("/getTeachers/{pageNo}/{pageSize}")
    public Result getTeachers(@PathVariable("pageNo") Integer pageNo, @PathVariable("pageSize") Integer pageSize,
            Teacher teacherName) {
        Page<Teacher> page = new Page<>(pageNo, pageSize);
        IPage<Teacher> iPage = teacherService.listTeacherByOpr(page, teacherName);
        return Result.ok(iPage);
    }

    @PostMapping("/saveOrUpdateTeacher")
    public Result saveOrUpdateTeacher(@RequestBody Teacher teacher) {
        if (teacher.getId() == null || teacher.getId() == 0) {
            teacher.setPassword(MD5.encrypt(teacher.getPassword()));
        }
        teacherService.saveOrUpdate(teacher);
        return Result.ok();
    }

    @DeleteMapping("/deleteTeacher")
    public Result deleteTeacher(@RequestBody List<Integer> ids) {
        teacherService.removeByIds(ids);
        return Result.ok();
    }
}
