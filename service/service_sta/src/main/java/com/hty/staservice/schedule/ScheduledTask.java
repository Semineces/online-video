package com.hty.staservice.schedule;

import com.hty.staservice.service.StatisticsDailyService;
import com.hty.staservice.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * create by Semineces on 2020-08-28
 */
@Component
public class ScheduledTask {

    @Autowired
    private StatisticsDailyService staService;

    //每隔五秒执行一次该方法
    /*@Scheduled(cron = "0/5 * * * * ?")
    public void task1() {
        System.out.println("----------执行task1");
    }*/

    //每天凌晨1点执行方法，把前一天查询出的数据进行添加
    @Scheduled(cron = "0 0 1 * * ?")
    public void task2() {
        staService.registerCount(DateUtil.formatDate(DateUtil.addDays(new Date(), -1)));
    }
}
