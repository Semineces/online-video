package com.hty.eduorder.controller;


import com.hty.eduorder.service.PayLogService;
import com.hty.utils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author Semineces
 * @since 2020-08-24
 */
@RestController
@RequestMapping("/eduorder/paylog")
@CrossOrigin
public class PayLogController {

    @Autowired
    private PayLogService payLogService;


    @ApiOperation("生成微信支付二维码接口，参数是订单号")
    @GetMapping("createNative/{orderNo}")
    public R createNative(@PathVariable String orderNo) {
        //返回信息，包含二维码地址，还有其他信息
        Map<String, Object> map = payLogService.createNative(orderNo);
        System.out.println("@@@@@返回二维码map集合 " + map);
        return R.success().data(map);
    }

    @ApiOperation("查询订单支付状态")
    @GetMapping("/queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable String orderNo) {
        Map<String, String> map = payLogService.queryPayStatus(orderNo);
        System.out.println("@@@@@查询订单支付状态map： " + map);
        if (map == null) {
            return R.error().message("支付出错");
        }
        //如果map不为空则通过map获取订单信息
        if (map.get("trade_state").equals("SUCCESS")) { //支付成功
            //添加记录到支付表，更新订单表订单状态
            payLogService.updateOrderStatus(map);
            return R.success().message("支付成功");
        }
        //前端25000 表明支付中，不做任何操作
        return R.success().code(25000).message("支付中");
    }
}

