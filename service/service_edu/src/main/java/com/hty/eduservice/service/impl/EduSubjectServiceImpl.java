package com.hty.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hty.eduservice.entity.EduSubject;
import com.hty.eduservice.entity.vo.EduSubjectVo;
import com.hty.eduservice.entity.subject.excel.SubjectData;
import com.hty.eduservice.listener.SubjectExcelListener;
import com.hty.eduservice.mapper.EduSubjectMapper;
import com.hty.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author Semineces
 * @since 2020-08-11
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void saveSubject(MultipartFile file, EduSubjectService eduSubjectService) {
        try {
            //文件输入流
            InputStream is = file.getInputStream();
            EasyExcel.read(is, SubjectData.class, new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<EduSubjectVo> getAllOneTwoSubject() {
        //查询出所有一级分类 pid = 0
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id", "0");
        //baseMapper来自ServiceImpl中 当然也可以直接this.list
        List<EduSubject> allOneSubjectList = baseMapper.selectList(wrapperOne);

        //查询出所有二级分类 pid != 0
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperOne.ne("parent_id", "0");
        List<EduSubject> allTwoSubjectList = baseMapper.selectList(wrapperTwo);

        //创建list集合用于存储最终封装数据
        List<EduSubjectVo> finalSubjectList = new ArrayList<>();

        //封装一级分类
        //查询出来的所有一级分类list集合遍历，得到每个一级分类对象，获取每个一级分类对象值
        //封装到要求的finalSubjectList中，得到每个EduSubject对象 放进去
        for (EduSubject one : allOneSubjectList) {
            //多个OneSubject放到finalSubjectList里面
            EduSubjectVo oneSubject = new EduSubjectVo();
            BeanUtils.copyProperties(one, oneSubject);
            finalSubjectList.add(oneSubject);

            //在一级分类循环遍历出所有二级分类
            //创建list集合封装每个一级分类的二级分类
            //封装二级分类
            List<EduSubjectVo> twoSubjectList = new ArrayList<>();
            for (EduSubject two : allTwoSubjectList) {
                //获取每个二级分类，并判断二级分类的parentId和一级分类id是否一样
                if (two.getParentId().equals(one.getId())) {
                    EduSubjectVo twoSubject = new EduSubjectVo();
                    BeanUtils.copyProperties(two, twoSubject);
                    twoSubjectList.add(twoSubject);
                }
            }
            //把二级分类放到一级里面
            oneSubject.setChildren(twoSubjectList);
        }
        return finalSubjectList;
    }
}
