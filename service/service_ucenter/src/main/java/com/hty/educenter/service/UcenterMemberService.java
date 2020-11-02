package com.hty.educenter.service;

import com.hty.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hty.educenter.entity.vo.RegisterVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author Semineces
 * @since 2020-08-18
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMember member);

    void register(RegisterVo member);

    Integer countRegisterDay(String day);

}
