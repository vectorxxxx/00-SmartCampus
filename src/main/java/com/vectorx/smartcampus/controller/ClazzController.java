package com.vectorx.smartcampus.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vectorx.smartcampus.pojo.Clazz;
import com.vectorx.smartcampus.service.ClazzService;
import com.vectorx.smartcampus.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sms/clazzController")
public class ClazzController
{
    @Autowired
    private ClazzService clazzService;

    @GetMapping("/getClazzsByOpr/{pageNo}/{pageSize}")
    public Result getClazzsByOpr(@PathVariable("pageNo") Integer pageNo, @PathVariable("pageSize") Integer pageSize,
            Clazz clazzName) {
        Page<Clazz> page = new Page<>(pageNo, pageSize);
        IPage<Clazz> iPage = clazzService.listClazzByOpr(page, clazzName);
        return Result.ok(iPage);
    }

    @GetMapping("/getClazzs")
    public Result getClazzs() {
        List<Clazz> clazzList = clazzService.listClazz();
        return Result.ok(clazzList);
    }

    @PostMapping("/saveOrUpdateClazz")
    public Result saveOrUpdateGrade(@RequestBody Clazz clazz) {
        clazzService.saveOrUpdate(clazz);
        return Result.ok();
    }

    @DeleteMapping("/deleteClazz")
    public Result deleteClazz(@RequestBody List<Integer> ids) {
        clazzService.removeByIds(ids);
        return Result.ok();
    }
}
