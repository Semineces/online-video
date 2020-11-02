package com.hty.eduservice.client;

import com.hty.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * create by Semineces on 2020-08-13
 * 从edu模块直接调用vod模块的内容
 */
@Component
//这个名字就是注册中心的名字 fallback就是出错了调用的方法
@FeignClient(name = "service-vod", fallback = VodFileDegradeFeignClient.class)
public interface VodClient {

    //根据视频id删除阿里云视频
    //定义调用的方法路径 如果有@PathVariable 则必须写上参数名称
    @DeleteMapping("/eduvod/video/removeAliyunVideo/{id}")
    R removeAliyunVideo(@PathVariable(value = "id") String id);

    //删除多个阿里云视频的方法，参数是多个视频id
    @DeleteMapping("/eduvod/video/deleteBatch")
    R deleteBatch(@RequestParam(value = "videoIdList") List<String> videoIdList);
}
