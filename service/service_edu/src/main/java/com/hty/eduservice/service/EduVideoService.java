package com.hty.eduservice.service;

import com.hty.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author Semineces
 * @since 2020-08-11
 */
public interface EduVideoService extends IService<EduVideo> {

    void removeByCourseId(String courseId);

    void deleteVideo(String videoId);
}
