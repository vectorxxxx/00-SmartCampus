package com.vectorx.smartcampus.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vectorx.smartcampus.pojo.Grade;
import com.vectorx.smartcampus.service.GradeService;
import com.vectorx.smartcampus.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "年级控制器")
@RestController
@RequestMapping("/sms/gradeController")
public class GradeController
{

    @Autowired
    private GradeService gradeService;

    @ApiOperation("获取所有年级信息")
    @GetMapping("/getGrades")
    public Result getGrades() {
        List<Grade> grades = gradeService.listGrade();
        return Result.ok(grades);
    }

    @ApiOperation("分页查询年级信息")
    @GetMapping("/getGrades/{pageNo}/{pageSize}")
    public Result getGradesByOpr(@PathVariable("pageNo") Integer pageNo, @PathVariable("pageSize") Integer pageSize,
            @ApiParam("过滤年级名称") String gradeName) {
        Page<Grade> page = new Page<>(pageNo, pageSize);
        IPage<Grade> iPage = gradeService.listGradeByOpr(page, gradeName);
        return Result.ok(iPage);
    }

    @ApiOperation("保存更新年级信息")
    @PostMapping("/saveOrUpdateGrade")
    public Result saveOrUpdateGrade(@RequestBody Grade grade) {
        gradeService.saveOrUpdate(grade);
        return Result.ok();
    }

    @ApiOperation("（批量）删除年级信息")
    @DeleteMapping("/deleteGrade")
    public Result deleteGrade(@RequestBody List<Integer> ids) {
        gradeService.removeByIds(ids);
        return Result.ok();
    }

}
