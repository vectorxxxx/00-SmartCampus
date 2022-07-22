package com.vectorx.smartcampus.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vectorx.smartcampus.pojo.Student;
import com.vectorx.smartcampus.service.StudentService;
import com.vectorx.smartcampus.util.MD5;
import com.vectorx.smartcampus.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sms/studentController")
public class StudentController
{
    @Autowired
    private StudentService studentService;

    @GetMapping("/getStudentByOpr/{pageNo}/{pageSize}")
    public Result getStudentByOpr(@PathVariable("pageNo") Integer pageNo, @PathVariable("pageSize") Integer pageSize,
            Student studentName) {
        Page<Student> page = new Page<>(pageNo, pageSize);
        IPage<Student> iPage = studentService.listStudentByOpr(page, studentName);
        return Result.ok(iPage);
    }

    @PostMapping("/addOrUpdateStudent")
    public Result addOrUpdateStudent(@RequestBody Student student) {
        if (student.getId() == null || student.getId() == 0) {
            student.setPassword(MD5.encrypt(student.getPassword()));
        }
        studentService.saveOrUpdate(student);
        return Result.ok();
    }

    @DeleteMapping("/delStudentById")
    public Result delStudentById(@RequestBody List<Integer> ids) {
        studentService.removeByIds(ids);
        return Result.ok();
    }
}
