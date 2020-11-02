package com.hty.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.hty.servicebase.MyException;
import com.hty.utils.R;
import com.hty.utils.ResultCode;
import com.hty.vod.service.VodService;
import com.hty.vod.utils.ConstantVodUtils;
import com.hty.vod.utils.InitVodClient;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * create by Semineces on 2020-08-13
 */
@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;

    @ApiOperation(value = "上传视频到阿里云")
    @PostMapping("/uploadAliyunVideo")
    public R uploadAliyunVideo(MultipartFile file) {
        String videoId = vodService.uploadVideo(file);
        return R.success().data("videoId", videoId);
    }

    @ApiOperation(value = "根据视频id删除阿里云的内容")
    @DeleteMapping("removeAliyunVideo/{id}")
    public R removeAliyunVideo(@PathVariable String id) {
        if (!StringUtils.isEmpty(id)) {
            try {
                //初始化对象
                DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
                //创建删除视频request对象
                DeleteVideoRequest request = new DeleteVideoRequest();
                //向request里面设置视频id
                request.setVideoIds(id);
                //调用初始化对象的方法实现删除
                client.getAcsResponse(request);
                return R.success();
            } catch (Exception e) {
                e.printStackTrace();
                throw new MyException(ResultCode.ERROR, "删除失败");
            }
        } else {
            return R.error().message("删除失败");
        }
    }

    @ApiOperation(value = "删除多个阿里云视频的方法，参数是多个视频id")
    @DeleteMapping("deleteBatch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList) {
        vodService.removeBatch(videoIdList);
        return R.success();
    }

    @ApiOperation(value = "根据视频id获取视频凭证")
    @GetMapping("/getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable String id) {
        try {
            //创建初始化对象
            DefaultAcsClient client =
                    InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            //创建获取凭证request
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            //向request里面设置视频id
            request.setVideoId(id);
            //获取response对象 调用方法得到凭证
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return R.success().data("playAuth", playAuth);
        } catch (Exception e) {
            throw new MyException(ResultCode.ERROR, "获取视频凭证失败");
        }
    }
}
