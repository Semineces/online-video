package com.hty.demo.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

/**
 * create by Semineces on 2020-08-11
 */
public class TestEasyExcel {

    public static void main(String[] args) {
        //实现excel写的操作
        //1.设置写入的文件夹地址和excel文件
        //String fileName = "D:\\test\\write.xlsx";
        //2.调用easyexcel实现写操作
        //EasyExcel.write(fileName, DemoData.class).sheet("学生列表").doWrite(getData());

        //实现excel读操作
        String fileName = "D:\\test\\write.xlsx";
        EasyExcel.read(fileName, DemoData.class, new ExcelListener()).sheet().doRead();
    }

    //创建一个方法返回list集合
    private static List<DemoData> getData() {
        List<DemoData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DemoData demoData = new DemoData();
            demoData.setSno(i);
            demoData.setSname("hty" + i);
            list.add(demoData);
        }
        return list;
    }
}
