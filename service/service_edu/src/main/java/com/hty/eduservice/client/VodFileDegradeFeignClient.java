package com.hty.eduservice.client;

import com.hty.utils.R;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * create by Semineces on 2020-08-13
 * 熔断器的实现类
 */
@Component
public class VodFileDegradeFeignClient implements VodClient {

    //如果在调用的时候出错，进行输出
    @Override
    public R removeAliyunVideo(String id) {
        return R.error().message("删除视频出错");
    }

    @Override
    public R deleteBatch(List<String> videoIdList) {
        return R.error().message("删除多个视频出错");
    }
}
