package com.hty.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.hty.oss.service.OssService;
import com.hty.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * create by Semineces on 2020-08-11
 */
@Service
public class OssServiceImpl implements OssService {

    //上传文件到OSS
    @Override
    public String uploadFileAvatar(MultipartFile file) {

        String endpoint = ConstantPropertiesUtils.END_POINT;
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        try {
            //创建OSSClient实例
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            //上传文件流
            InputStream inputStream = file.getInputStream();
            //获取文件名称
            String fileName = file.getOriginalFilename();

            //1.在文件名称里面添加随机唯一值，后面的replaceAll就是替换
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            fileName = uuid + fileName;
            //2.把文件按照日期进行分类
            //路径2020//11/11/01.jpg
            //获取当前日期
            String datePath = new DateTime().toString("yyyy/MM/dd");
            //拼接
            fileName = datePath + "/" + fileName;

            //第一个参数是bucketName
            //第二个参数上传到oss文件路径和文件名称/aa/bb/1.png
            ossClient.putObject(bucketName, fileName, inputStream);
            //关闭OSSClient。
            ossClient.shutdown();

            //把上传之后的文件路径返回
            //需要把上传到阿里云oss的路径手动拼接
            return "https://" + bucketName + "." + endpoint + "/" + fileName;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
