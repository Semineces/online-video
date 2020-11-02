package com.hty.eduservice.controller;


import com.hty.eduservice.entity.EduChapter;
import com.hty.eduservice.entity.vo.chapter.ChapterVo;
import com.hty.eduservice.service.EduChapterService;
import com.hty.utils.R;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author Semineces
 * @since 2020-08-11
 */
@RestController
@RequestMapping("/eduservice/chapter")
public class EduChapterController {

    @Autowired
    private EduChapterService eduChapterService;

    @ApiOperation(value = "课程大纲列表")
    @GetMapping("/getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId) {
        if (!StringUtils.isEmpty(courseId)) {
            //根据课程id去查询章节小节
            List<ChapterVo> chapterVoList = eduChapterService.getChapterVideoByCourseId(courseId);
            return R.success().data("allChapterVideo", chapterVoList);
        } else {
            return R.error().message("未查询到数据！");
        }
    }

    @ApiOperation(value = "添加章节")
    @PostMapping("/addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter) {
        eduChapterService.save(eduChapter);
        return R.success();
    }

    @ApiOperation(value = "根据章节id查询")
    @GetMapping("/getChapterInfo/{chapterId}")
    public R getChapterInfo(@PathVariable String chapterId) {
        if (!StringUtils.isEmpty(chapterId)) {
            EduChapter eduChapter = eduChapterService.getById(chapterId);
            return R.success().data("chapter", eduChapter);
        } else {
            return R.error().message("未查询到数据！");
        }

    }

    @ApiOperation(value = "修改章节")
    @PostMapping("/updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter) {
        eduChapterService.updateById(eduChapter);
        return R.success();
    }

    @ApiOperation(value = "删除章节")
    @DeleteMapping("/deleteChapter/{chapterId}")
    public R deleteChapter(@PathVariable String chapterId) {
        if (!StringUtils.isEmpty(chapterId)) {
            boolean result = eduChapterService.deleteChapter(chapterId);
            return result ? R.success() : R.error().message("删除失败");
        } else {
            return R.error().message("未查询到数据！");
        }
    }

}

