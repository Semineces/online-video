package com.hty.eduservice.service;

import com.hty.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hty.eduservice.entity.vo.EduSubjectVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author Semineces
 * @since 2020-08-11
 */
public interface EduSubjectService extends IService<EduSubject> {

    //添加课程分类
    void saveSubject(MultipartFile file, EduSubjectService eduSubjectService);

    //获取分类树状图
    List<EduSubjectVo> getAllOneTwoSubject();
}
