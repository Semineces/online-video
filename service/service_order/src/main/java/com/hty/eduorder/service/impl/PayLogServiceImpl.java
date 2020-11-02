package com.hty.eduorder.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.wxpay.sdk.WXPayUtil;
import com.hty.eduorder.entity.Order;
import com.hty.eduorder.entity.PayLog;
import com.hty.eduorder.mapper.PayLogMapper;
import com.hty.eduorder.service.OrderService;
import com.hty.eduorder.service.PayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hty.eduorder.utils.HttpClient;
import com.hty.servicebase.MyException;
import com.hty.utils.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author Semineces
 * @since 2020-08-24
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {

    @Autowired
    private OrderService orderService;

    //生成微信支付二维码接口，参数是订单号
    @Override
    public Map<String, Object> createNative(String orderNo) {
        try {
            //1.根据订单号查询订单信息
            QueryWrapper<Order> wrapper = new QueryWrapper<>();
            wrapper.eq("order_no", orderNo);
            Order order = orderService.getOne(wrapper);
            //2.使用map设置生成二维码需要参数
            Map<String, String> map = new HashMap<>();
            map.put("appid", "wx74862e0dfcf69954"); //关联的公众号
            map.put("mch_id", "1558950191"); //商户号
            map.put("nonce_str", WXPayUtil.generateNonceStr()); //商户key
            map.put("body", order.getCourseTitle()); //课程标题
            map.put("out_trade_no", orderNo); //订单号
            //把价格变为字符串
            map.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue() + "");
            map.put("spbill_create_ip", "127.0.0.1"); //后面是项目的ip
            map.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify\n");
            map.put("trade_type", "NATIVE");
            //3.发送httpClient请求传递参数 传递参数xml格式，微信支付提供的固定的地址
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            //设置xml格式的参数
            client.setXmlParam(WXPayUtil.generateSignedXml(map, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            //执行post请求发送
            client.post();
            //4.得到发送请求返回结果
            //返回的内容是使用xml格式返回
            String xml = client.getContent();
            //把xml转换成map集合
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
            //返回最终数据封装，前端要什么返回什么
            Map<String, Object> m = new HashMap<>();
            m.put("out_trade_no", orderNo); //订单号
            m.put("course_id", order.getCourseId()); //课程id
            m.put("total_fee", order.getTotalFee()); //总价格
            m.put("result_code", resultMap.get("result_code"));  //返回二维码操作状态码
            m.put("code_url", resultMap.get("code_url"));        //二维码地址
            return m;
        } catch (Exception e) {
            throw new MyException(ResultCode.ERROR, "加载失败");
        }
    }

    //查询订单支付状态
    @Override
    public Map<String, String> queryPayStatus(String orderNo) {
        //1.封装参数，都是固定值
        Map<String, String> m = new HashMap<>();
        m.put("appid", "wx74862e0dfcf69954");
        m.put("mch_id", "1558950191");
        m.put("out_trade_no", orderNo);
        m.put("nonce_str", WXPayUtil.generateNonceStr());
        try {
            //2.发送HttpClient请求
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery"); //微信固定查询接口
            client.setXmlParam(WXPayUtil.generateSignedXml(m, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();

            //3.得到请求返回内容
            String xml = client.getContent();
            return WXPayUtil.xmlToMap(xml);
        } catch (Exception e) {
            throw new MyException(ResultCode.ERROR, "查询异常");
        }
    }

    //添加支付记录和更新订单状态
    @Override
    public void updateOrderStatus(Map<String, String> map) {
        //从map中获取订单号
        String orderNo = map.get("out_trade_no");

        //根据订单号查询订单信息
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderNo);
        Order order = orderService.getOne(wrapper);

        //更新订单表里订单的状态
        if (order.getStatus() == 1) { //1代表已经支付了，不需要改
            return;
        }
        order.setStatus(1);
        orderService.updateById(order);

        //给支付表里面添加支付记录
        PayLog payLog = new PayLog();
        payLog.setOrderNo(orderNo);
        payLog.setPayTime(new Date()); //订单完成时间
        payLog.setPayType(1);//支付类型 1微信
        payLog.setTotalFee(order.getTotalFee());//总金额(分)
        payLog.setTradeState(map.get("trade_state"));//支付状态
        payLog.setTransactionId(map.get("transaction_id")); //流水号
        payLog.setAttr(JSONObject.toJSONString(map));

        baseMapper.insert(payLog);
    }
}
