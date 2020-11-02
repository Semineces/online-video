package com.hty.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hty.eduservice.entity.EduCourse;
import com.hty.eduservice.entity.EduTeacher;
import com.hty.eduservice.service.EduCourseService;
import com.hty.eduservice.service.EduTeacherService;
import com.hty.utils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * create by Semineces on 2020-08-19
 */
@RestController
@RequestMapping("/eduservice/teacherfront")
@CrossOrigin
public class TeacherFrontController {

    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseService courseService;

    @ApiOperation("分页查询讲师")
    @GetMapping("/getTeacherFrontList/{page}/{limit}")
    public R getTeacherFrontList(@PathVariable Long page, @PathVariable Long limit) {
        Page<EduTeacher> teacherPage = new Page<>(page, limit);
        //这么做的目的是，前端不用element-ui 后端就要返回所有的数据
        Map<String, Object> map = teacherService.getTeacherFrontList(teacherPage);
        //返回分页的所有数据
        return R.success().data(map);
    }

    @ApiOperation("讲师详情")
    @GetMapping("/getTeacherFrontInfo/{teacherId}")
    public R getTeacherFrontInfo(@PathVariable String teacherId) {
        //1.根据讲师id查询讲师基本信息
        EduTeacher eduTeacher = teacherService.getById(teacherId);
        //2.根据讲师id查询所有课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id", teacherId);
        List<EduCourse> courseList = courseService.list(wrapper);

        return R.success().data("teacher", eduTeacher).data("courseList", courseList);
    }

}
