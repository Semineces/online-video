package com.hty.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hty.educenter.entity.UcenterMember;
import com.hty.eduservice.client.UcenterClient;
import com.hty.eduservice.entity.EduComment;
import com.hty.eduservice.entity.vo.CommentVo;
import com.hty.eduservice.mapper.EduCommentMapper;
import com.hty.eduservice.service.EduCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hty.servicebase.MyException;
import com.hty.utils.JwtUtil;
import com.hty.utils.ResultCode;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author Semineces
 * @since 2020-09-14
 */
@Service
public class EduCommentServiceImpl extends ServiceImpl<EduCommentMapper, EduComment> implements EduCommentService {

    @Autowired
    private UcenterClient ucenterClient;

    //分页获取评论列表，由于只有评论，直接获取即可，评论回复留个坑
    @Override
    public List<CommentVo> getCommentList(String courseId) {
        QueryWrapper<EduComment> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("gmt_create");
        //按照时间倒序查出来所有的评论
        List<EduComment> commentList = baseMapper.selectList(wrapper);
        //创建一个返回的结果集
        List<CommentVo> commentVoList = new ArrayList<>();
        //创建一个vo对象用于返回
        CommentVo commentVo = new CommentVo();
        //将查询的结果按照要求封装
        for (EduComment comment : commentList) {
            BeanUtils.copyProperties(comment, commentVo);
            commentVoList.add(commentVo);
        }
        return commentVoList;
    }

    /**
     * TODO: 根据用户的token直接查出来头像和昵称
     *
     * @param commentVo
     * @return
     */
    @Override
    public boolean saveComment(CommentVo commentVo) {
        if (commentVo.getAvatar() == null) throw new MyException(ResultCode.ERROR, "未登录");
        EduComment eduComment = new EduComment();
        BeanUtils.copyProperties(commentVo, eduComment);
        int insert = baseMapper.insert(eduComment);
        return insert > 0;
    }

}
