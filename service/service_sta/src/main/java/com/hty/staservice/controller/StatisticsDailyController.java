package com.hty.staservice.controller;


import com.hty.staservice.service.StatisticsDailyService;
import com.hty.utils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author Semineces
 * @since 2020-08-28
 */
@RestController
@RequestMapping("/staservice/sta")
@CrossOrigin
public class StatisticsDailyController {

    @Autowired
    private StatisticsDailyService staService;

    @ApiOperation("统计某一天注册人数，生成统计数据")
    @PostMapping("/registerCount/{day}")
    public R registerCount(@PathVariable String day) {
        staService.registerCount(day);
        return R.success();
    }

    @ApiOperation("图标显示，返回两部分数据，日期json数组，数量json数组")
    @GetMapping("/showData/{type}/{begin}/{end}")
    public R showData(@PathVariable String type, @PathVariable String begin, @PathVariable String end) {
        Map<String, Object> map = staService.getShowData(type, begin, end);
        return R.success().data(map);
    }
}

