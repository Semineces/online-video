package com.hty.eduservice.entity.vo.chapter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * create by Semineces on 2020-08-11
 * 章节
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChapterVo {

    private String id;

    private String title;

    //表示小节
    private List<VideoVo> children = new ArrayList<>();
}
