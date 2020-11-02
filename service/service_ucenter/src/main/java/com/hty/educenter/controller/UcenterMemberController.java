package com.hty.educenter.controller;


import com.hty.educenter.entity.UcenterMember;
import com.hty.educenter.entity.vo.RegisterVo;
import com.hty.educenter.service.UcenterMemberService;
import com.hty.utils.JwtUtil;
import com.hty.utils.R;
import com.hty.utils.ordervo.UcenterMemberOrder;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author Semineces
 * @since 2020-08-18
 */
@RestController
@RequestMapping("/educenter/member")
@CrossOrigin
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService memberService;

    @ApiOperation("登录")
    @PostMapping("/login")
    public R login(@RequestBody UcenterMember member) {
        //member对象方正手机号和密码
        //调用service的方法
        //返回一个token，使用JWT生成
        String token = memberService.login(member);
        return R.success().data("token", token);
    }

    @ApiOperation("注册，手机验证码的方法")
    @PostMapping("/register")
    public R register(@RequestBody RegisterVo registerVo) {
        memberService.register(registerVo);
        return R.success();
    }

    @ApiOperation("根据token字符串获取用户信息")
    @GetMapping("/getMemberInfo")
    public R getMemberInfo(HttpServletRequest request) {
        //调用jwt工具类的方法，根据request对象获取头信息，返回用户id
        String memberId = JwtUtil.getMemberIdByJwtToken(request);
        //数据库中查询用户信息
        UcenterMember member = memberService.getById(memberId);
        return R.success().data("userInfo", member);
    }

    @GetMapping("/getMemberInfoForComment")
    public UcenterMember getMemberInfoForComment(HttpServletRequest request) {
        //调用jwt工具类的方法，根据request对象获取头信息，返回用户id
        String memberId = JwtUtil.getMemberIdByJwtToken(request);
        //数据库中查询用户信息
        return memberService.getById(memberId);
    }

    //为了订单方便取值，直接返回一对象即可。在common里面写UcenterMemberOrder，让调用方和被调方都用这个对象
    @ApiOperation("根据用户id获取用户信息")
    @PostMapping("/getUserInfoOrder/{id}")
    public UcenterMemberOrder getUserInfoOrder(@PathVariable String id) {
        UcenterMember member = memberService.getById(id);
        //把member对象里面的值赋给UcenterMemberOrder
        UcenterMemberOrder ucenterMemberOrder = new UcenterMemberOrder();
        BeanUtils.copyProperties(member, ucenterMemberOrder);
        return ucenterMemberOrder;
    }

    @ApiOperation("查询某一天注册人数")
    @GetMapping("/countRegister/{day}")
    public R countRegister(@PathVariable String day) {
        Integer count = memberService.countRegisterDay(day);
        return R.success().data("countRegister", count);
    }


}

