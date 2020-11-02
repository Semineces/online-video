package com.hty.eduorder.client;

import com.hty.utils.ordervo.CourseOrderVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * create by Semineces on 2020-08-24
 */
@Component
@FeignClient("service-edu")
public interface EduClient {

    @PostMapping("/eduservice/coursefront/getCourseInfoOrder/{id}")
    CourseOrderVo getCourseInfoOrder(@PathVariable("id") String id);
}
