package com.hty.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * create by Semineces on 2020-08-11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseInfoVo {

    @ApiModelProperty(value = "课程id")
    private String id;

    @ApiModelProperty(value = "教师id")
    private String teacherId;

    @ApiModelProperty(value = "课程分类id")
    private String subjectId;

    @ApiModelProperty(value = "课程分类父类id")
    private String subjectParentId;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "价格，0免费观看")
    //0.01效果用BigDecimal
    private BigDecimal price;

    @ApiModelProperty(value = "总课时")
    private Integer lessonNum;

    @ApiModelProperty(value = "封面图片路径")
    private String cover;

    @ApiModelProperty(value = "课程简介")
    private String description;
}
