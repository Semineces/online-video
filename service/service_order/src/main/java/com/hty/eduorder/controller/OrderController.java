package com.hty.eduorder.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hty.eduorder.entity.Order;
import com.hty.eduorder.service.OrderService;
import com.hty.utils.JwtUtil;
import com.hty.utils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author Semineces
 * @since 2020-08-24
 */
@RestController
@RequestMapping("/eduorder/order")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    @ApiOperation("生成订单的方法")
    @PostMapping("/createOrder/{courseId}")
    public R createOrder(@PathVariable String courseId, HttpServletRequest request) {
        //创建订单，返回订单号
        String orderId = orderService.createOrders(courseId, JwtUtil.getMemberIdByJwtToken(request));
        return R.success().data("orderId", orderId);
    }

    @ApiOperation("根据订单id查询订单信息")
    @GetMapping("/getOrderInfo/{orderId}")
    public R getOrderInfo(@PathVariable String orderId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderId);
        Order order = orderService.getOne(wrapper);
        return R.success().data("item", order);
    }

    @ApiOperation("根据课程id和用户id查询订单表中的订单状态。如果支付了返回true，没有返回false")
    @GetMapping("/isBuyCourse/{courseId}/{memberId}")
    public Boolean isBuyCourse(@PathVariable String courseId, @PathVariable String memberId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        //根据课程id，用户id，支付状态去查
        wrapper.eq("course_id", courseId);
        wrapper.eq("member_id", memberId);
        wrapper.eq("status", 1);
        int count = orderService.count(wrapper);
        //返回count是否大于0
        return count > 0;
    }
}

