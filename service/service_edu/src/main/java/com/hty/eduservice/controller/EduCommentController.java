package com.hty.eduservice.controller;


import com.hty.eduservice.entity.EduComment;
import com.hty.eduservice.entity.vo.CommentVo;
import com.hty.eduservice.service.EduCommentService;
import com.hty.utils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author Semineces
 * @since 2020-09-14
 */
@RestController
@RequestMapping("/eduservice/educomment")
public class EduCommentController {

    @Autowired
    private EduCommentService commentService;

    @ApiOperation("添加评论回复")
    @PostMapping("/addComment")
    public R addComment(@RequestBody CommentVo commentVo) {
        boolean save = commentService.saveComment(commentVo);
        return save ? R.success() : R.error().message("评论失败");
    }

    @ApiOperation("分页查询评论回复")
    @GetMapping("/getCommentList/{courseId}")
    public R getCommentList(@PathVariable String courseId) {
        List<CommentVo> commentList = commentService.getCommentList(courseId);
        return R.success().data("commentList", commentList);
    }


}

