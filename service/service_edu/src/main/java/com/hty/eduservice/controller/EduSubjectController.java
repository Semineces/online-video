package com.hty.eduservice.controller;


import com.hty.eduservice.entity.vo.EduSubjectVo;
import com.hty.eduservice.service.EduSubjectService;
import com.hty.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author Semineces
 * @since 2020-08-11
 */
@RestController
@RequestMapping("/eduservice/subject")
public class EduSubjectController {

    @Autowired
    private EduSubjectService eduSubjectService;

    //添加课程分类
    //获取到上传过的文件 把文件内容读取出来
    @PostMapping("/addSubject")
    public R addSubject(MultipartFile file) {
        //上传过来excel文件
        eduSubjectService.saveSubject(file, eduSubjectService);
        return R.success();
    }

    //课程分类列表
    @GetMapping("/getAllSubject")
    public R getAllSubject() {
        //一级分类 一级分类有其本身还有二级分类
        List<EduSubjectVo> list = eduSubjectService.getAllOneTwoSubject();
        return R.success().data("list", list);
    }
}

