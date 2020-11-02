package com.hty.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hty.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hty.eduservice.entity.vo.CourseInfoVo;
import com.hty.eduservice.entity.vo.PublishCourseInfoVo;
import com.hty.eduservice.entity.vo.front.CourseFrontInfoVo;
import com.hty.eduservice.entity.vo.front.CourseFrontVo;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author Semineces
 * @since 2020-08-11
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfoVo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    PublishCourseInfoVo getPublishCourseInfo(String courseId);

    void removeCourse(String courseId);

    Map<String, Object> getCourseFrontList(Page<EduCourse> coursePage, CourseFrontVo courseFrontVo);

    CourseFrontInfoVo getBaseCourseInfo(String courseId);
}
