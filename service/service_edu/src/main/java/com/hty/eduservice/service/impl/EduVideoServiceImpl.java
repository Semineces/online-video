package com.hty.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hty.eduservice.client.VodClient;
import com.hty.eduservice.entity.EduVideo;
import com.hty.eduservice.mapper.EduVideoMapper;
import com.hty.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hty.servicebase.MyException;
import com.hty.utils.R;
import com.hty.utils.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author Semineces
 * @since 2020-08-11
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VodClient vodClient;

    //根据courseId删除小节 同时删除对应的阿里云视频文件
    @Override
    public void removeByCourseId(String courseId) {
        //先删除小节中的所有视频 批量
        //根据课程id查询课程所有视频的id
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id", courseId);
        wrapperVideo.select("video_source_id");
        List<EduVideo> eduVideoList = baseMapper.selectList(wrapperVideo);
        //把List<EduVideo> 变成List<String>
        List<String> videoIds = new ArrayList<>();
        for (EduVideo v : eduVideoList) {
            if (!StringUtils.isEmpty(v.getVideoSourceId())) {
                videoIds.add(v.getVideoSourceId());
            }
        }
        if (!videoIds.isEmpty()) {
            //nacos远程调用
            vodClient.deleteBatch(videoIds);
        }

        //然后删除小节
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        baseMapper.delete(wrapper);
    }

    //删除小节 同时删除阿里云中的视频 nacos远程调用
    @Override
    public void deleteVideo(String videoId) {
        //先删视频再删小节 A里面有B 先删B后删A 先删小再删大
        EduVideo eduVideo = baseMapper.selectById(videoId);
        String videoSourceId = eduVideo.getVideoSourceId();
        if (!StringUtils.isEmpty(videoSourceId)) {
            R result = vodClient.removeAliyunVideo(videoSourceId);
            //如果在调用过程中失败了 就熔断器
            if (result.getCode() == 20001) {
                throw new MyException(ResultCode.ERROR, "删除视频失败，启动熔断器");
            }
        }
        baseMapper.deleteById(videoId);
    }
}
