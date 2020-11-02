package com.hty.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hty.eduservice.entity.EduChapter;
import com.hty.eduservice.entity.EduVideo;
import com.hty.eduservice.entity.vo.chapter.ChapterVo;
import com.hty.eduservice.entity.vo.chapter.VideoVo;
import com.hty.eduservice.mapper.EduChapterMapper;
import com.hty.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hty.eduservice.service.EduVideoService;
import com.hty.servicebase.MyException;
import com.hty.utils.ResultCode;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author Semineces
 * @since 2020-08-11
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService eduVideoService;

    //根据课程id去查询章节小节
    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        //1.根据课程id查询课程里面所有的章节
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id", courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(wrapperChapter);

        //2.根据课程id查询课程里面所有的小节
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperChapter.eq("course_id", courseId);
        List<EduVideo> eduVideoList = eduVideoService.list(wrapperVideo);
        //创建list集合用于最终封装数据
        List<ChapterVo> finalList = new ArrayList<>();

        //3.遍历查询章节list集合进行封装
        for (EduChapter eduChapter : eduChapterList) {
            ChapterVo chapterVo = new ChapterVo();
            //把EduChapter复制到vo中
            BeanUtils.copyProperties(eduChapter, chapterVo);
            //创建集合用于封装章节的小节
            List<VideoVo> videoVoList = new ArrayList<>();
            //4.遍历查询小节list集合进行封装
            for (EduVideo eduVideo : eduVideoList) {
                //判断小节里面的chapterid里面id是否一样
                if (eduVideo.getChapterId().equals(eduChapter.getId())) {
                    //进行封装
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo, videoVo);
                    videoVoList.add(videoVo);
                }
            }
            //把封装后的小节list集合 放到章节对象里面
            chapterVo.setChildren(videoVoList);
            //把chapterVo放在最终的list中
            finalList.add(chapterVo);
        }

        return finalList;
    }

    @Override
    public boolean deleteChapter(String chapterId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id", chapterId);
        //查询有无数据，如果章节下面有小节，那么不让删除
        //否则，可以删除
        int count = eduVideoService.count(wrapper);
        if (count > 0) {
            throw new MyException(ResultCode.ERROR, "存在关联数据，不能删除！");
        } else {
            int result = baseMapper.deleteById(chapterId);
            return result > 0;
        }
    }

    @Override
    public void removeByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        baseMapper.delete(wrapper);
    }
}
