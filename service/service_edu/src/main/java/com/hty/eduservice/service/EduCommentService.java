package com.hty.eduservice.service;

import com.hty.eduservice.entity.EduComment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hty.eduservice.entity.vo.CommentVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author Semineces
 * @since 2020-09-14
 */
public interface EduCommentService extends IService<EduComment> {

    List<CommentVo> getCommentList(String courseId);

    boolean saveComment(CommentVo commentVo);
}
