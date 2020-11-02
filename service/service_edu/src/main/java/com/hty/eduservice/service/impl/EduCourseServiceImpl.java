package com.hty.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hty.eduservice.entity.EduCourse;
import com.hty.eduservice.entity.EduCourseDescription;
import com.hty.eduservice.entity.EduTeacher;
import com.hty.eduservice.entity.vo.CourseInfoVo;
import com.hty.eduservice.entity.vo.PublishCourseInfoVo;
import com.hty.eduservice.entity.vo.front.CourseFrontInfoVo;
import com.hty.eduservice.entity.vo.front.CourseFrontVo;
import com.hty.eduservice.mapper.EduCourseMapper;
import com.hty.eduservice.service.EduChapterService;
import com.hty.eduservice.service.EduCourseDescriptionService;
import com.hty.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hty.eduservice.service.EduVideoService;
import com.hty.servicebase.MyException;
import com.hty.utils.ResultCode;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author Semineces
 * @since 2020-08-11
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private EduChapterService eduChapterService;

    @Override
    public String saveCourseInfoVo(CourseInfoVo courseInfoVo) {
        //1.给课程表添加课程基本信息
        //CourseInfoVo对象转换eduCourse对象
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if (0 == insert) {
            //添加失败
            throw new MyException(20001, "添加课程信息失败");
        }

        String cid = eduCourse.getId();
        //2.给课程简介表中添加课程简介
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        //设置描述id就是课程id
        eduCourseDescription.setId(cid);
        eduCourseDescriptionService.save(eduCourseDescription);
        return cid;
    }

    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        //1 查询课程表
        EduCourse eduCourse = baseMapper.selectById(courseId);
        if (null != eduCourse) {
            BeanUtils.copyProperties(eduCourse, courseInfoVo);
        }
        //2 查询描述表
        EduCourseDescription courseDescription = eduCourseDescriptionService.getById(courseId);
        if (null != courseDescription) {
            courseInfoVo.setDescription(courseDescription.getDescription());
            return courseInfoVo;
        } else throw new MyException(ResultCode.ERROR, "查询失败");
    }

    @Transactional
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        //1.修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if (0 == update) {
            throw new MyException(ResultCode.ERROR, "修改课程失败");
        }

        //2.修改描述表
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(courseInfoVo.getId());
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        boolean b = eduCourseDescriptionService.updateById(eduCourseDescription);
        if (!b) {
            throw new MyException(ResultCode.ERROR, "修改课程失败");
        }
    }

    @Override
    public PublishCourseInfoVo getPublishCourseInfo(String courseId) {
        if (!StringUtils.isEmpty(courseId)) {
            //调用mapper
            return baseMapper.getCoursePublishVo(courseId);
        } else {
            throw new MyException(ResultCode.ERROR, "获取信息失败");
        }
    }

    @Override
    public void removeCourse(String courseId) {
        //根据课程id删除小节
        eduVideoService.removeByCourseId(courseId);
        //根据课程id删除章节
        eduChapterService.removeByCourseId(courseId);
        //根据课程id删除课程描述
        eduCourseDescriptionService.removeById(courseId);
        //根据课程id删除课程本身
        int result = baseMapper.deleteById(courseId);
        if (result == 0) {
            throw new MyException(ResultCode.ERROR, "删除失败");
        }
    }

    //条件查询带分页查询教程
    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> coursePage, CourseFrontVo courseFrontVo) {
        //根据讲师id查询所讲课程 可能附带条件
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        //判断条件值是否为空，不为空拼接
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())) { //一级分类是否为空
            wrapper.eq("subject_parent_id", courseFrontVo.getSubjectParentId());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectId())) { //二级分类是否为空
            wrapper.eq("subject_id", courseFrontVo.getSubjectId());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())) { //关注度（购买量）
            wrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) { //更新时间
            wrapper.orderByDesc("gmt_create");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())) { //价格
            wrapper.orderByDesc("price");
        }
        baseMapper.selectPage(coursePage, wrapper);

        //分页
        List<EduCourse> records = coursePage.getRecords(); //每页数据的List集合
        Long current = coursePage.getCurrent(); //当前是第几页页
        Long pages = coursePage.getPages(); //总页数
        Long size = coursePage.getSize(); //每页记录数
        Long total = coursePage.getTotal(); //总记录数
        Boolean hasNext = coursePage.hasNext(); //是否有下一页
        Boolean hasPrevious = coursePage.hasPrevious(); //是否有上一页
        //把分页的数据获取出来，放到map集合
        Map<String, Object> map = new HashMap<>();
        map.put("records", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;
    }

    //前端根据课程id查询基本信息
    @Override
    public CourseFrontInfoVo getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }
}
