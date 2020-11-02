package com.hty.eduservice.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * create by Semineces on 2020-08-11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EduSubjectVo {

    private String id;

    private String title;

    //一个一级分类有多个二级分类
    private List<EduSubjectVo> children = new ArrayList<>();

}
