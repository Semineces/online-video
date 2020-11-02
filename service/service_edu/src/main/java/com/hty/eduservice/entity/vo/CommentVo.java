package com.hty.eduservice.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * create by Semineces on 2020-09-14
 * 前端获取的评论vo类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentVo {

    private String courseId;

    private String id;

    private String nickname;

    private String avatar;

    private String content;

}
