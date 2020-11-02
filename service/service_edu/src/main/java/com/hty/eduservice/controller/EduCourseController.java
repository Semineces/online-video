package com.hty.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hty.eduservice.entity.EduCourse;
import com.hty.eduservice.entity.vo.CourseInfoVo;
import com.hty.eduservice.entity.vo.CourseQueryVo;
import com.hty.eduservice.entity.vo.PublishCourseInfoVo;
import com.hty.eduservice.service.EduCourseService;
import com.hty.utils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author Semineces
 * @since 2020-08-11
 */
@RestController
@RequestMapping("/eduservice/course")
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;

    @ApiOperation(value = "添加课程基本信息")
    @PostMapping("/addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        String id = eduCourseService.saveCourseInfoVo(courseInfoVo);
        return R.success().data("courseId", id);
    }

    @ApiOperation(value = "根据课程id查询课程的基本信息")
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId) {
        if (!StringUtils.isEmpty(courseId)) {
            CourseInfoVo courseInfoVo = eduCourseService.getCourseInfo(courseId);
            return R.success().data("courseInfoVo", courseInfoVo);
        } else {
            return R.error().message("没有查询到数据！");
        }

    }

    @ApiOperation(value = "更新课程信息")
    @PostMapping("/updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        eduCourseService.updateCourseInfo(courseInfoVo);
        return R.success();
    }

    @ApiOperation(value = "根据课程id查询课程确认信息")
    @GetMapping("/getCoursePublishInfo/{courseId}")
    public R getPublishCourseInfo(@PathVariable String courseId) {
        if (!StringUtils.isEmpty(courseId)) {
            PublishCourseInfoVo publishCourseInfoVo = eduCourseService.getPublishCourseInfo(courseId);
            return R.success().data("publishCourse", publishCourseInfoVo);
        } else {
            return R.error().message("查询失败");
        }
    }

    @ApiOperation(value = "课程最终发布（修改课程状态）")
    @PostMapping("/publishCourse/{courseId}")
    public R publishCourse(@PathVariable String courseId) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(courseId);
        eduCourse.setStatus("Normal");
        boolean result = eduCourseService.updateById(eduCourse);
        if (result) {
            return R.success();
        } else {
            return R.error().message("发布失败");
        }
    }

    @ApiOperation(value = "课程最终发布的分页列表（条件查询）当前页和每页的记录数")
    @PostMapping("/pageCourseCondition/{current}/{size}")
    public R pageCourseCondition(@PathVariable Integer current, @PathVariable Integer size,
                                 @RequestBody(required = false) CourseQueryVo courseQueryVo) {
        Page<EduCourse> pageCourse = new Page<>(current, size);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        String title = courseQueryVo.getTitle();
        String status = courseQueryVo.getStatus();
        if (!StringUtils.isEmpty(title)) {
            wrapper.like("title", title);
        }
        if (!StringUtils.isEmpty(status)) {
            wrapper.eq("status", status);
        }
        wrapper.orderByDesc("gmt_create");
        eduCourseService.page(pageCourse, wrapper);
        long total = pageCourse.getTotal();
        List<EduCourse> courseList = pageCourse.getRecords();
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("records", courseList);
        return R.success().data(map);
    }

    @ApiOperation(value = "删除课程")
    @DeleteMapping("/deleteCourse/{courseId}")
    public R deleteCourse(@PathVariable String courseId) {
        eduCourseService.removeCourse(courseId);
        return R.success();
    }
}

