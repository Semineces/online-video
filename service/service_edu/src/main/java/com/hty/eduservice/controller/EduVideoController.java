package com.hty.eduservice.controller;


import com.hty.eduservice.client.VodClient;
import com.hty.eduservice.entity.EduVideo;
import com.hty.eduservice.service.EduVideoService;
import com.hty.utils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author Semineces
 * @since 2020-08-11
 */
@RestController
@RequestMapping("/eduservice/video")
public class EduVideoController {

    @Autowired
    private EduVideoService eduVideoService;

    @ApiOperation(value = "添加小节")
    @PostMapping("/addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo) {
        boolean result = eduVideoService.save(eduVideo);
        return result ? R.success() : R.success().message("添加失败");
    }

    @ApiOperation(value = "修改小节")
    @PostMapping("/updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo) {
        boolean result = eduVideoService.updateById(eduVideo);
        return result ? R.success() : R.success().message("修改失败");
    }

    @ApiOperation(value = "删除小节 并且也要删除阿里云上的视频")
    @DeleteMapping("/deleteVideo/{videoId}")
    public R deleteVideo(@PathVariable String videoId) {
        if (!StringUtils.isEmpty(videoId)) {
            eduVideoService.deleteVideo(videoId);
            return R.success();
        } else {
            return R.error().message("找不到数据");
        }
    }
}

