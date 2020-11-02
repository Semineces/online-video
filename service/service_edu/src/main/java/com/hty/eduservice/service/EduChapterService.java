package com.hty.eduservice.service;

import com.hty.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hty.eduservice.entity.vo.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author Semineces
 * @since 2020-08-11
 */
public interface EduChapterService extends IService<EduChapter> {

    //根据课程id去查询章节小节
    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    boolean deleteChapter(String chapterId);

    void removeByCourseId(String courseId);
}
