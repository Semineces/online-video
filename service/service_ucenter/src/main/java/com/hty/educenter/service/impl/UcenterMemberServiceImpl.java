package com.hty.educenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hty.educenter.entity.UcenterMember;
import com.hty.educenter.entity.vo.RegisterVo;
import com.hty.educenter.mapper.UcenterMemberMapper;
import com.hty.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hty.servicebase.MyException;
import com.hty.utils.JwtUtil;
import com.hty.utils.MD5;
import com.hty.utils.ResultCode;
import com.sun.deploy.net.proxy.pac.PACFunctions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author Semineces
 * @since 2020-08-18
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    //登录的方法
    @Override
    public String login(UcenterMember member) {
        //获取登录手机号和密码
        String mobile = member.getMobile();
        String password = member.getPassword();

        //手机号密码非空校验
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new MyException(ResultCode.ERROR, "登录失败");
        }

        //判断手机号是否正确
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        UcenterMember ucenterMember = baseMapper.selectOne(wrapper); //返回的是UcenterMember对象
        //不存在该手机号，抛出异常
        if (StringUtils.isEmpty(ucenterMember)) {
            throw new MyException(ResultCode.ERROR, "手机号不存在");
        }

        //判断密码，输入的密码经过MD5加密后，跟数据库中的密码是否相等
        //数据库中不存储明文，存储的是MD5加密的，只能加密不能解密
        if (!MD5.encrypt(password).equals(ucenterMember.getPassword())) {
            throw new MyException(ResultCode.ERROR, "用户名或密码错误");
        }

        //判断用户名是否禁用
        if (ucenterMember.getIsDisabled()) {
            throw new MyException(ResultCode.ERROR, "用户被禁用");
        }

        //以上的条件都不满足，登录成功
        //生成token字符串，使用jwt工具类。传入的是后面查询出来正确的的ucenterMember
        String jwtToken = JwtUtil.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());

        return jwtToken;
    }

    @Override
    public void register(RegisterVo registerVo) {
        //获取注册的数据
        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();

        //非空判断
        if (StringUtils.isEmpty(code) || StringUtils.isEmpty(mobile)
                || StringUtils.isEmpty(nickname) || StringUtils.isEmpty(password)) {
            throw new MyException(ResultCode.ERROR, "注册失败");
        }

        //判断验证码
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if (!code.equals(redisCode)) {
            throw new MyException(ResultCode.ERROR, "验证码错误");
        }

        //判断手机号是否存在
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        if (baseMapper.selectCount(wrapper) > 0) {
            throw new MyException(ResultCode.ERROR, "手机号已存在！");
        }

        //最终添加
        UcenterMember member = new UcenterMember();
        member.setMobile(mobile);
        member.setNickname(nickname);
        member.setPassword(MD5.encrypt(password));
        member.setIsDisabled(false); //用户默认不禁用
        //设置默认头像
        member.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        baseMapper.insert(member);
    }

    //查询某一天注册人数
    @Override
    public Integer countRegisterDay(String day) {
        return baseMapper.countRegisterDay(day);
    }

}
