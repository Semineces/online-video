package com.hty.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hty.eduservice.entity.EduSubject;
import com.hty.eduservice.entity.subject.excel.SubjectData;
import com.hty.eduservice.service.EduSubjectService;
import com.hty.servicebase.MyException;

/**
 * create by Semineces on 2020-08-11
 */
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    //SubjectExcelListener不能交给spring进行管理，需要自己new 不能注入其他对象 所以要自己手动创建无参有参
    //不能实现数据库操作
    public EduSubjectService eduSubjectService;

    public SubjectExcelListener() {
    }

    public SubjectExcelListener(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }

    //一行一行读取excel内容
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if (subjectData == null) {
            throw new MyException(20001, "文件为空");
        }
        //一行一行读取 每次读取两个值 第一个值一级分类 第二个二级分类
        //判断一级分类是否重复
        EduSubject oneSubject = this.existOneSubject(subjectData.getOneSubjectName(), eduSubjectService);
        if (oneSubject == null) { //没有相同一级分类 进行添加
            oneSubject = new EduSubject();
            oneSubject.setParentId("0");
            oneSubject.setTitle(subjectData.getOneSubjectName());
            eduSubjectService.save(oneSubject);
        }

        //获取一级分类id值
        String pid = oneSubject.getId();
        //添加二级分类
        //判断二级分类是否重复
        EduSubject twoSubject = this.existTwoSubject(subjectData.getTwoSubjectName(), pid, eduSubjectService);
        if (twoSubject == null) {
            twoSubject = new EduSubject();
            twoSubject.setParentId(pid);
            twoSubject.setTitle(subjectData.getTwoSubjectName());
            eduSubjectService.save(twoSubject);
        }

    }

    //一级分类不能重复添加 搜索一下看看什么是以及分类
    private EduSubject existOneSubject(String title, EduSubjectService eduSubjectService) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", title);
        wrapper.eq("parent_id", "0");
        return eduSubjectService.getOne(wrapper);
    }

    //二级分类不能重复添加
    private EduSubject existTwoSubject(String title, String pid, EduSubjectService eduSubjectService) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", title);
        wrapper.eq("parent_id", pid);
        return eduSubjectService.getOne(wrapper);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
