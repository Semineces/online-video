package com.hty.eduservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * create by Semineces on 2020-08-25
 */
@Component
@FeignClient("service-order")
public interface OrderClient {

    //定义接口路径
    @GetMapping("/eduorder/order/isBuyCourse/{courseId}/{memberId}")
    Boolean isBuyCourse(@PathVariable("courseId") String courseId, @PathVariable("memberId") String memberId);
}
