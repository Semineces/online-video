package com.hty.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hty.eduservice.entity.EduTeacher;
import com.hty.eduservice.mapper.EduTeacherMapper;
import com.hty.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author Semineces
 * @since 2020-08-04
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    //1.分页查询讲师
    @Override
    public Map<String, Object> getTeacherFrontList(Page<EduTeacher> teacherPage) {
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        //根据讲师id降序
        wrapper.orderByDesc("id");
        baseMapper.selectPage(teacherPage, wrapper);

        List<EduTeacher> records = teacherPage.getRecords(); //每页数据的List集合
        Long current = teacherPage.getCurrent(); //当前是第几页页
        Long pages = teacherPage.getPages(); //总页数
        Long size = teacherPage.getSize(); //每页记录数
        Long total = teacherPage.getTotal(); //总记录数
        Boolean hasNext = teacherPage.hasNext(); //是否有下一页
        Boolean hasPrevious = teacherPage.hasPrevious(); //是否有上一页
        //把分页的数据获取出来，放到map集合
        Map<String, Object> map = new HashMap<>();
        map.put("records", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        //返回map
        return map;
    }
}
