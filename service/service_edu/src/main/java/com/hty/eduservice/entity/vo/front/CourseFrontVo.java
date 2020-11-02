package com.hty.eduservice.entity.vo.front;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * create by Semineces on 2020-08-21
 * 前端课程的条件筛选vo 目的是做一个显示
 */
@Data
public class CourseFrontVo {

    @ApiModelProperty(value = "课程名称")
    private String title;

    @ApiModelProperty(value = "讲师id")
    private String teacherId;

    @ApiModelProperty(value = "一级类别id")
    private String subjectParentId;

    @ApiModelProperty(value = "二级类别id")
    private String subjectId;

    @ApiModelProperty(value = "销量排序")
    private String buyCountSort;

    @ApiModelProperty(value = "最新时间排序")
    private String gmtCreateSort;

    @ApiModelProperty(value = "价格排序")
    private String priceSort;
}
