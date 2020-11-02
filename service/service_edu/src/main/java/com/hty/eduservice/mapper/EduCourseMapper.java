package com.hty.eduservice.mapper;

import com.hty.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hty.eduservice.entity.vo.PublishCourseInfoVo;
import com.hty.eduservice.entity.vo.front.CourseFrontInfoVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author Semineces
 * @since 2020-08-11
 */

public interface EduCourseMapper extends BaseMapper<EduCourse> {

    PublishCourseInfoVo getCoursePublishVo(@Param("courseId") String courseId);

    CourseFrontInfoVo getBaseCourseInfo(String courseId);
}
