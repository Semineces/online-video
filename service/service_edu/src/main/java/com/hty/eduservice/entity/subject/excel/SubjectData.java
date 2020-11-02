package com.hty.eduservice.entity.subject.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * create by Semineces on 2020-08-11
 * Excel对应实体类
 */
@Data
public class SubjectData {

    @ExcelProperty(index = 0)
    private String oneSubjectName;

    @ExcelProperty(index = 1)
    private String twoSubjectName;
}
