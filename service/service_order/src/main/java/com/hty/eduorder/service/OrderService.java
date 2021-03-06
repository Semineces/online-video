package com.hty.eduorder.service;

import com.hty.eduorder.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author Semineces
 * @since 2020-08-24
 */
public interface OrderService extends IService<Order> {

    String createOrders(String courseId, String memberId);
}
