package com.hty.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hty.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author Semineces
 * @since 2020-08-04
 */
public interface EduTeacherService extends IService<EduTeacher> {

    Map<String, Object> getTeacherFrontList(Page<EduTeacher> teacherPage);
}
