package com.hty.eduorder.service.impl;

import com.hty.eduorder.client.EduClient;
import com.hty.eduorder.client.UcenterClient;
import com.hty.eduorder.entity.Order;
import com.hty.eduorder.mapper.OrderMapper;
import com.hty.eduorder.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hty.eduorder.utils.OrderNoUtil;
import com.hty.utils.ordervo.CourseOrderVo;
import com.hty.utils.ordervo.UcenterMemberOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author Semineces
 * @since 2020-08-24
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private UcenterClient ucenterClient;

    @Autowired
    private EduClient eduClient;

    //生成订单的方法
    @Override
    public String createOrders(String courseId, String memberId) {
        //通过远程调用根据用户id获取用户信息
        UcenterMemberOrder userInfoOrder = ucenterClient.getUserInfoOrder(memberId);
        //通过远程调用根据课程id获取课程信息
        CourseOrderVo courseInfoOrder = eduClient.getCourseInfoOrder(courseId);
        //创建order对象，向order对象里面设置需要数据
        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo()); //订单号
        order.setCourseId(courseId); //课程id
        order.setCourseTitle(courseInfoOrder.getTitle()); //课程标题
        order.setCourseCover(courseInfoOrder.getCover()); //课程封面
        order.setTeacherName(courseInfoOrder.getTeacherName()); //教师姓名
        order.setTotalFee(courseInfoOrder.getPrice()); //课程价格
        order.setMemberId(memberId); //用户id
        order.setMobile(userInfoOrder.getMobile()); //用户手机
        order.setNickname(userInfoOrder.getNickname()); //用户昵称
        order.setStatus(0);  //订单状态（0：未支付 1：已支付）
        order.setPayType(1);  //支付类型 ，微信1
        baseMapper.insert(order);
        //返回订单号
        return order.getOrderNo();
    }
}
