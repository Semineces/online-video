package com.hty.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hty.eduservice.entity.EduCourse;
import com.hty.eduservice.entity.EduTeacher;
import com.hty.eduservice.service.EduCourseService;
import com.hty.eduservice.service.EduTeacherService;
import com.hty.utils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * create by Semineces on 2020-08-14
 */
@RestController
@RequestMapping("/eduservice/indexfront")
@CrossOrigin
public class IndexFrontController {

    @Autowired
    private EduCourseService eduCourseService;

    @Autowired
    private EduTeacherService eduTeacherService;

    @ApiOperation("查询前8条热门课程，查询前四条名师")
    @Cacheable(value = "index", unless = "#result eq null")
    @GetMapping("/index")
    public R index() {
        //查询前八条热门课程
        QueryWrapper<EduCourse> wrapperCourse = new QueryWrapper<>();
        wrapperCourse.orderByDesc("id");
        wrapperCourse.last("limit 8");
        List<EduCourse> listCourse = eduCourseService.list(wrapperCourse);

        //查询前四条名师
        QueryWrapper<EduTeacher> wrapperTeacher = new QueryWrapper<>();
        wrapperTeacher.orderByDesc("id");
        wrapperTeacher.last("limit 4");
        List<EduTeacher> listTeacher = eduTeacherService.list(wrapperTeacher);

        return R.success().data("eduList", listCourse).data("teacherList", listTeacher);
    }
}
