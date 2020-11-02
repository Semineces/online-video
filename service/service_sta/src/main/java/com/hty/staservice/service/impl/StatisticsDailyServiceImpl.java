package com.hty.staservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hty.staservice.client.UcenterClient;
import com.hty.staservice.entity.StatisticsDaily;
import com.hty.staservice.mapper.StatisticsDailyMapper;
import com.hty.staservice.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hty.utils.R;
import com.hty.utils.RandomUtil;
import org.apache.commons.lang3.RandomUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author Semineces
 * @since 2020-08-28
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private UcenterClient ucenterClient;

    //统计某一天注册人数，生成统计数据
    @Override
    public void registerCount(String day) {
        //添加记录之前先做判断，删除相同日期的数据
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated", day);
        baseMapper.delete(wrapper);

        //远程调用得到某一天注册人数
        R registerR = ucenterClient.countRegister(day);
        Integer countRegister = (Integer) registerR.getData().get("countRegister");

        //把获取数据添加到数据库，统计到表中
        StatisticsDaily sta = new StatisticsDaily();
        sta.setRegisterNum(countRegister); //注册人数
        sta.setDateCalculated(day); //统计日期
        //模拟观看次数
        sta.setVideoViewNum(RandomUtils.nextInt(100, 200));
        sta.setLoginNum(RandomUtils.nextInt(100, 200));
        sta.setCourseNum(RandomUtils.nextInt(100, 200));

        baseMapper.insert(sta);
    }

    //图标显示，返回两部分数据，日期json数组，数量json数组
    @Override
    public Map<String, Object> getShowData(String type, String begin, String end) {
        //根据条件查询对应数据
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated", begin, end);
        //根据查询type以及date_calculated日期
        wrapper.select("date_calculated", type);
        List<StatisticsDaily> staList = baseMapper.selectList(wrapper);

        //因为返回的有两部分数据，日期，日期对应数量
        //前端要求json数组，对应Java中的List。建立两个List，一个表示日期，一个表示日期对应数量
        List<String> dateCalculatedList = new ArrayList<>();
        List<Integer> numDateList = new ArrayList<>();
        //遍历查询出来所有数据list集合，进行封装
        for (StatisticsDaily daily : staList) {
            //封装日期list集合
            dateCalculatedList.add(daily.getDateCalculated());
            //封装日期对应的数量
            switch (type) {
                case "login_num":
                    numDateList.add(daily.getLoginNum());
                    break;
                case "register_num":
                    numDateList.add(daily.getRegisterNum());
                    break;
                case "video_vies_num":
                    numDateList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    numDateList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
        }

        //将数据封装到map集合中，进行返回
        Map<String, Object> map = new HashMap<>();
        map.put("dateCalculatedList", dateCalculatedList);
        map.put("numDateList", numDateList);
        return map;
    }
}
