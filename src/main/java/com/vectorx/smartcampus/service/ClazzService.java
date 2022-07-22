package com.vectorx.smartcampus.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.vectorx.smartcampus.pojo.Clazz;

import java.util.List;

public interface ClazzService extends IService<Clazz>
{

    IPage<Clazz> listClazzByOpr(Page<Clazz> page, Clazz clazz);

    List<Clazz> listClazz();
}
