package com.hty.eduservice.entity.vo.chapter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * create by Semineces on 2020-08-11
 * 小节
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoVo {

    private String id;

    private String title;

    //视频id（阿里云存储的）
    private String videoSourceId;
}
