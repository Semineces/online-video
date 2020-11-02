package com.hty.eduservice.client;

import com.hty.educenter.entity.UcenterMember;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * create by Semineces on 2020-09-14
 */
@Component
@FeignClient("service-ucenter")
public interface UcenterClient {

    @GetMapping("/educenter/member/getMemberInfoForComment")
    UcenterMember getMemberInfoForComment(HttpServletRequest request);
}
